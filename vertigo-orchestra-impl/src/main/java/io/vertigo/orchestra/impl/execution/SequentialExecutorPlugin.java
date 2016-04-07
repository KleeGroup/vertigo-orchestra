package io.vertigo.orchestra.impl.execution;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OActivityExecutionDAO;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.dao.execution.OActivityWorkspaceDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.domain.execution.OActivityWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ActivityLogger;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.NodeManager;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;
import io.vertigo.util.ClassUtil;
import io.vertigo.util.StringUtil;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class SequentialExecutorPlugin implements Plugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(SequentialExecutorPlugin.class);

	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private OActivityExecutionDAO activityExecutionDAO;
	@Inject
	private OActivityWorkspaceDAO activityWorkspaceDAO;
	@Inject
	private ExecutionPAO executionPAO;
	@Inject
	private OActivityLogDAO activityLogDAO;
	@Inject
	private OActivityDAO activityDAO;

	private final Long workersCount;
	private final Long nodId;
	private final ExecutorService workers;
	private final ScheduledExecutorService localScheduledExecutor;
	private final long timerDelay;

	private final NodeManager nodeManager;
	private final ProcessSchedulerManager processSchedulerManager;
	private final VTransactionManager transactionManager;

	@Inject
	public SequentialExecutorPlugin(
			final ProcessSchedulerManager processSchedulerManager,
			final NodeManager nodeManager,
			final VTransactionManager transactionManager,
			@Named("nodeName") final String nodeName,
			@Named("workersCount") final Long workersCount,
			@Named("executionPeriod") final Integer executionPeriod) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(processSchedulerManager);
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(workersCount);
		Assertion.checkNotNull(executionPeriod);
		// ---
		Assertion.checkState(workersCount >= 1, "We need at least 1 worker");
		// ---
		this.nodeManager = nodeManager;
		this.processSchedulerManager = processSchedulerManager;
		this.transactionManager = transactionManager;
		// We register the node
		nodId = nodeManager.registerNode(nodeName);
		// ---
		Assertion.checkNotNull(nodId);
		// ---
		this.workersCount = (long) workersCount;
		timerDelay = 1000 * executionPeriod;
		workers = Executors.newFixedThreadPool(workersCount.intValue());
		localScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	/** {@inheritDoc} */
	@Override
	public void start() {

		localScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					executeToDo();
					nodeManager.updateHeartbeat(nodId);
				} catch (final Exception e) {
					// We log the error and we continue the timer
					LOGGER.error("Exception launching activities to executes", e);
				}

			}
		}, timerDelay / 2, timerDelay, TimeUnit.MILLISECONDS);

	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		localScheduledExecutor.shutdown();
		workers.shutdown();
	}

	//--------------------------------------------------------------------------------------------------
	//--- Package
	//--------------------------------------------------------------------------------------------------

	/**
	 * TODO : Description de la méthode.
	 * @param activityExecution
	 * @param workspaceOut
	 */
	void putResult(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspaceOut, final Throwable error) {
		Assertion.checkNotNull(workspaceOut);
		Assertion.checkNotNull(workspaceOut.getValue("status"), "Le status est obligatoire dans le résultat");
		// ---
		// 2. We manage the execution workflow
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			if (error != null) {
				error.printStackTrace();
				doChangeExecutionState(activityExecution, ExecutionState.ERROR);
			} else {
				if (workspaceOut.isSuccess()) {
					endActivityExecutionAndInitNext(activityExecution);
				} else {
					doChangeExecutionState(activityExecution, ExecutionState.ERROR);
				}
			}
			transaction.commit();
		}

	}

	void changeExecutionState(final OActivityExecution activityExecution, final ExecutionState executionState) {
		if (transactionManager.hasCurrentTransaction()) {
			doChangeExecutionState(activityExecution, executionState);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doChangeExecutionState(activityExecution, executionState);
				transaction.commit();
			}
		}
	}

	private void doChangeExecutionState(final OActivityExecution activityExecution, final ExecutionState executionState) {
		Assertion.checkNotNull(activityExecution);
		// ---
		activityExecution.setEstCd(executionState.name());
		activityExecutionDAO.save(activityExecution);

		// If it's an error the entire process is in Error
		if (ExecutionState.ERROR.equals(executionState)) {
			endProcessExecution(activityExecution.getPreId(), ExecutionState.ERROR);
		}

	}

	ActivityExecutionWorkspace getWorkspaceForActivityExecution(final Long aceId, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		// ---
		final OActivityWorkspace activityWorkspace = activityWorkspaceDAO.getActivityWorkspace(aceId, in);
		return new ActivityExecutionWorkspace(activityWorkspace.getWorkspace());
	}

	void saveActivityExecutionWorkspace(final Long aceId, final ActivityExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		Assertion.checkNotNull(workspace);
		// ---
		final OActivityWorkspace activityWorkspace = new OActivityWorkspace();
		activityWorkspace.setAceId(aceId);
		activityWorkspace.setIsIn(in);
		activityWorkspace.setWorkspace(workspace.getStringForStorage());

		activityWorkspaceDAO.save(activityWorkspace);

	}

	/** {@inheritDoc} */
	ActivityExecutionWorkspace execute(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspace) {
		ActivityExecutionWorkspace resultWorkspace = workspace;

		try {
			changeExecutionState(activityExecution, ExecutionState.RUNNING);
			// ---
			final ActivityEngine activityEngine = Injector.newInstance(
					ClassUtil.classForName(activityExecution.getEngine(), ActivityEngine.class), Home.getApp().getComponentSpace());

			try {
				// We try the execution and we keep the result
				resultWorkspace = activityEngine.execute(workspace);

			} catch (final Exception e) {
				// In case of failure we keep the current workspace
				resultWorkspace.setFailure();

			} finally {
				try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
					// We save the workspace which is the minimal state
					saveActivityExecutionWorkspace(activityExecution.getAceId(), resultWorkspace, false);
					if (activityEngine instanceof AbstractActivityEngine) {
						// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...)
						saveActivityLogs(activityExecution.getAceId(), ((AbstractActivityEngine) activityEngine).getLogger());
					}
					transaction.commit();
				}
			}

		} catch (final Exception e) {
			// Informative log
			resultWorkspace.setFailure();
			logError(activityExecution, e);
		}

		return resultWorkspace;
	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private void executeToDo() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			initNewProcessesToLaunch();
			transaction.commit();
		}
		final DtList<OActivityExecution> activitiesToLaunch;
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			activitiesToLaunch = getActivitiesToLaunch();
			transaction.commit();
		}
		for (final OActivityExecution activityExecution : activitiesToLaunch) { //We submit only the process we can handle, no queue
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				final ActivityExecutionWorkspace workspace = getWorkspaceForActivityExecution(activityExecution.getAceId(), true);
				doChangeExecutionState(activityExecution, ExecutionState.SUBMITTED);
				// We set the beginning time of the activity
				activityExecution.setBeginTime(new Date());
				workers.submit(new OWorker(activityExecution, workspace, this));
				transaction.commit();
			}
		}

	}

	private void initFirstAcitvityExecution(final OProcessExecution processExecution, final OProcessPlanification processPlanification) {
		Assertion.checkNotNull(processExecution.getProId());
		Assertion.checkNotNull(processExecution.getPreId());
		// ---
		final OActivity firstActivity = getFirtActivityByProcess(processExecution.getProId());
		final OActivityExecution firstActivityExecution = initActivityExecutionWithActivity(firstActivity, processExecution.getPreId());
		activityExecutionDAO.save(firstActivityExecution);

		final ActivityExecutionWorkspace initialWorkspace;
		if (!StringUtil.isEmpty(processPlanification.getInitialParams())) {
			// If Plannification specifies initialParams we take them
			initialWorkspace = new ActivityExecutionWorkspace(processPlanification.getInitialParams());
		} else {
			// Otherwise we take the process initial params for the firstWorkspace
			initialWorkspace = new ActivityExecutionWorkspace(processExecution.getProcess().getInitialParams());

		}
		saveActivityExecutionWorkspace(firstActivityExecution.getAceId(), initialWorkspace, true);

	}

	private OProcessExecution initProcessExecution(final OProcessPlanification processPlanification) {
		final OProcess process = processPlanification.getProcessus();
		final OProcessExecution newProcessExecution = new OProcessExecution();
		newProcessExecution.setProId(process.getProId());
		newProcessExecution.setBeginTime(new Date());
		newProcessExecution.setEstCd(ExecutionState.RUNNING.name());

		saveProcessExecution(newProcessExecution);

		return newProcessExecution;
	}

	private void endActivityExecutionAndInitNext(final OActivityExecution activityExecution) {
		endActivity(activityExecution);

		final Option<OActivity> nextActivity = getNextActivityByActId(activityExecution.getActId());
		if (nextActivity.isDefined()) {
			final OActivityExecution nextActivityExecution = initActivityExecutionWithActivity(nextActivity.get(), activityExecution.getPreId());
			// We keep the previous worker (Not the same but the slot) for the next Activity Execution
			reserveActivityExecution(nextActivityExecution);
			activityExecutionDAO.save(nextActivityExecution);
			// We keep the old workspace for the nextTask
			final ActivityExecutionWorkspace previousWorkspace = getWorkspaceForActivityExecution(activityExecution.getAceId(), false);
			// We remove the status
			previousWorkspace.resetStatus();
			saveActivityExecutionWorkspace(nextActivityExecution.getAceId(), previousWorkspace, true);

		} else {
			endSuccessfulProcessExecution(activityExecution.getPreId());
		}
	}

	private void initNewProcessesToLaunch() {
		for (final Long prpId : processSchedulerManager.getProcessToExecute()) {
			final OProcessPlanification processPlanification = processPlanificationDAO.get(prpId);
			if (canExecute(processPlanification)) {
				final OProcessExecution processExecution = initProcessExecution(processPlanification);
				processSchedulerManager.triggerPlanification(prpId);
				initFirstAcitvityExecution(processExecution, processPlanification);
			} else {
				processSchedulerManager.misfirePlanification(prpId);
			}
		}
	}

	private DtList<OActivityExecution> getActivitiesToLaunch() {
		final Long maxNumber = getUnusedWorkersCount();
		Assertion.checkNotNull(maxNumber);
		// ---
		executionPAO.reserveActivitiesToLaunch(nodId, maxNumber);
		return activityExecutionDAO.getActivitiesToLaunch(nodId);
	}

	private static OActivityExecution initActivityExecutionWithActivity(final OActivity activity, final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		final OActivityExecution newActivityExecution = new OActivityExecution();

		newActivityExecution.setPreId(preId);
		newActivityExecution.setActId(activity.getActId());
		newActivityExecution.setCreationTime(new Date());
		newActivityExecution.setEngine(activity.getEngine());
		newActivityExecution.setEstCd(ExecutionState.WAITING.name());

		return newActivityExecution;

	}

	private void reserveActivityExecution(final OActivityExecution activityExecution) {
		activityExecution.setEstCd(ExecutionState.RESERVED.name());
		activityExecution.setNodId(nodId);
	}

	private void endActivity(final OActivityExecution activityExecution) {
		activityExecution.setEndTime(new Date());
		activityExecution.setEstCd(ExecutionState.DONE.name());
		activityExecutionDAO.save(activityExecution);

	}

	private OProcessExecution getProcessExecutionById(final Long preId) {
		return processExecutionDAO.get(preId);
	}

	private void saveProcessExecution(final OProcessExecution processExecution) {
		processExecutionDAO.save(processExecution);

	}

	private void endProcessExecution(final Long preId, final ExecutionState executionState) {
		final OProcessExecution processExecution = getProcessExecutionById(preId);
		processExecution.setEndTime(new Date());
		processExecution.setEstCd(executionState.name());
		saveProcessExecution(processExecution);

	}

	private void endSuccessfulProcessExecution(final Long preId) {
		endProcessExecution(preId, ExecutionState.DONE);

	}

	private boolean canExecute(final OProcessPlanification processPlanification) {
		// We check if process allow multiExecutions
		final OProcess process = processPlanification.getProcessus();
		if (!process.getMultiexecution()) {
			return processExecutionDAO.getActiveProcessExecutionByProId(process.getProId()).isEmpty();
		}
		return true;

	}

	private void saveActivityLogs(final Long aceId, final ActivityLogger activityLogger) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(activityLogger);
		//
		if (transactionManager.hasCurrentTransaction()) {
			doSaveActivityLogs(aceId, activityLogger);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doSaveActivityLogs(aceId, activityLogger);
				transaction.commit();
			}
		}

	}

	private void doSaveActivityLogs(final Long aceId, final ActivityLogger activityLogger) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(activityLogger);
		//
		final OActivityLog activityLog = new OActivityLog();
		activityLog.setAceId(aceId);
		activityLog.setLog(activityLogger.getLogAsString());
		activityLogDAO.save(activityLog);
	}

	private Long getUnusedWorkersCount() {
		Assertion.checkNotNull(workers);
		// ---
		if (workers instanceof ThreadPoolExecutor) {
			final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) workers;
			return workersCount - workersPool.getActiveCount();
		}
		return workersCount;
	}

	private static void logError(final OActivityExecution activityExecution, final Throwable e) {
		LOGGER.error("Erreur de l'activité : " + activityExecution.getEngine(), e);
	}

	private OActivity getFirtActivityByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return activityDAO.getFirstActivityByProcess(proId);
	}

	private Option<OActivity> getNextActivityByActId(final Long actId) {
		Assertion.checkNotNull(actId);
		// ---
		return activityDAO.getNextActivityByActId(actId);
	}

}
