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

	private final int workersCount;
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
			@Named("workersCount") final int workersCount,
			@Named("executionPeriodSeconds") final int executionPeriodSeconds) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(processSchedulerManager);
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(nodeName);
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
		this.workersCount = workersCount;
		timerDelay = executionPeriodSeconds * 1000L;
		workers = Executors.newFixedThreadPool(workersCount);
		localScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		handleDeadNodeProcesses();

		localScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					executeToDo();
					nodeManager.updateHeartbeat(nodId);
					handleDeadNodeProcesses();
				} catch (final Exception e) {
					// We log the error and we continue the timer
					LOGGER.error("Exception launching activities to executes", e);
				}

			}
		}, 0, timerDelay, TimeUnit.MILLISECONDS);

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
		if (error != null) {
			// We log the error and we continue the timer
			LOGGER.info("Error in activity " + activityExecution.getActId() + " execution", error);
			endActivityExecution(activityExecution, ExecutionState.ERROR);
		} else {
			if (workspaceOut.isSuccess()) {
				endActivityExecution(activityExecution, ExecutionState.DONE);
			} else if (workspaceOut.isFinished()) {
				// If finished we tag the whole process as DONE and dont launch next activities
				finishProcessExecution(activityExecution);
			} else if (workspaceOut.isPending()) {
				// We do nothing because we already delegated the change of status in the AbstractActivityEngine
			} else {
				endActivityExecution(activityExecution, ExecutionState.ERROR);
			}
		}

	}

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState executionState) {
		Assertion.checkNotNull(activityExecutionId);
		Assertion.checkNotNull(token);
		// ---
		OActivityExecution activityExecution;
		ActivityExecutionWorkspace workspace;
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			activityExecution = activityExecutionDAO.getActivityExecutionByToken(activityExecutionId, token);
			Assertion.checkNotNull(activityExecution, "Activity token and id are not compatible");
			workspace = getWorkspaceForActivityExecution(activityExecution.getAceId(), false);
			transaction.commit();
		}
		// ---
		Assertion.checkState(ExecutionState.PENDING.name().equals(activityExecution.getEstCd()), "Only pending executions can be ended remotly");
		Assertion.checkState(workspace != null, "Workspace for activityExecution not found");

		// We execute the postTreatment of the pending activity when it's released
		// ---
		final ActivityEngine activityEngine = Injector.newInstance(
				ClassUtil.classForName(activityExecution.getEngine(), ActivityEngine.class), Home.getApp().getComponentSpace());

		try {
			switch (executionState) {
				case DONE:
					workspace = activityEngine.successfulPostTreatment(workspace);
					break;
				case ERROR:
					workspace = activityEngine.errorPostTreatment(workspace, new RuntimeException("ThirdPartyExeception"));
					break;
				default:
					throw new UnsupportedOperationException();
			}

		} catch (final Exception e) {
			endActivityExecution(activityExecution, ExecutionState.ERROR);

		} finally {
			handleOtherServices(activityEngine, activityExecution, workspace);
		}

		// we continue with the standard workflow for ending executions
		endActivityExecution(activityExecution, executionState);

	}

	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		Assertion.checkNotNull(activityExecutionId);
		// ---
		final OActivityExecution activityExecution = activityExecutionDAO.get(activityExecutionId);
		endActivityExecution(activityExecution, ExecutionState.PENDING);
		saveActivityExecutionWorkspace(activityExecutionId, workspace, false);
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

	private void finishProcessExecution(final OActivityExecution activityExecution) {
		if (transactionManager.hasCurrentTransaction()) {
			doFinishProcessExecution(activityExecution);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doFinishProcessExecution(activityExecution);
				transaction.commit();
			}
		}
	}

	private void doFinishProcessExecution(final OActivityExecution activityExecution) {
		Assertion.checkNotNull(activityExecution);
		// ---
		activityExecution.setEstCd(ExecutionState.DONE.name());
		activityExecution.setEndTime(new Date());
		activityExecutionDAO.save(activityExecution);
		endProcessExecution(activityExecution.getPreId(), ExecutionState.DONE);
	}

	ActivityExecutionWorkspace getWorkspaceForActivityExecution(final Long aceId, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		// ---
		final OActivityWorkspace activityWorkspace = activityWorkspaceDAO.getActivityWorkspace(aceId, in).get();
		return new ActivityExecutionWorkspace(activityWorkspace.getWorkspace());
	}

	void saveActivityExecutionWorkspace(final Long aceId, final ActivityExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		Assertion.checkNotNull(workspace);
		// ---
		// we need at most one workspace in and one workspace out
		final OActivityWorkspace activityWorkspace = activityWorkspaceDAO.getActivityWorkspace(aceId, in).getOrElse(new OActivityWorkspace());
		activityWorkspace.setAceId(aceId);
		activityWorkspace.setIsIn(in);
		activityWorkspace.setWorkspace(workspace.getStringForStorage());

		activityWorkspaceDAO.save(activityWorkspace);

	}

	private void handleOtherServices(final ActivityEngine activityEngine, final OActivityExecution activityExecution, final ActivityExecutionWorkspace resultWorkspace) {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			// We save the workspace which is the minimal state
			saveActivityExecutionWorkspace(activityExecution.getAceId(), resultWorkspace, false);
			if (activityEngine instanceof AbstractActivityEngine) {
				// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...)
				saveActivityLogs(activityExecution.getAceId(), ((AbstractActivityEngine) activityEngine).getLogger(), resultWorkspace);
			}
			transaction.commit();
		}
	}

	ActivityExecutionWorkspace execute(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspace) {
		ActivityExecutionWorkspace resultWorkspace = workspace;

		try {
			changeExecutionState(activityExecution, ExecutionState.RUNNING);
			// ---
			final ActivityEngine activityEngine = Injector.newInstance(
					ClassUtil.classForName(activityExecution.getEngine(), ActivityEngine.class), Home.getApp().getComponentSpace());

			try {

				// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...) so we log the workspace
				if (activityEngine instanceof AbstractActivityEngine) {
					final String workspaceInLog = new StringBuilder("Workspace in :").append(workspace.getStringForStorage()).toString();
					((AbstractActivityEngine) activityEngine).getLogger().info(workspaceInLog);
				}
				// We try the execution and we keep the result
				resultWorkspace = activityEngine.execute(workspace);
				Assertion.checkNotNull(resultWorkspace);
				Assertion.checkNotNull(resultWorkspace.getValue("status"), "Le status est obligatoire dans le résultat");
				// if pending we delegated the treatment to a third party so we are not sure that we are successful
				if (!resultWorkspace.isPending()) {
					// we call the posttreament
					resultWorkspace = activityEngine.successfulPostTreatment(resultWorkspace);
				}

			} catch (final Exception e) {
				// In case of failure we keep the current workspace
				resultWorkspace.setFailure();
				logError(activityExecution, e);
				// we call the posttreament
				resultWorkspace = activityEngine.errorPostTreatment(resultWorkspace, e);

			} finally {
				handleOtherServices(activityEngine, activityExecution, resultWorkspace);
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
			ActivityExecutionWorkspace workspace;
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				workspace = getWorkspaceForActivityExecution(activityExecution.getAceId(), true);
				doChangeExecutionState(activityExecution, ExecutionState.SUBMITTED);
				// We set the beginning time of the activity
				activityExecution.setBeginTime(new Date());
				transaction.commit();
			}
			workers.submit(new OWorker(activityExecution, workspace, this));
		}

	}

	private void initFirstAcitvityExecution(final OProcessExecution processExecution, final OProcessPlanification processPlanification) {
		Assertion.checkNotNull(processExecution.getProId());
		Assertion.checkNotNull(processExecution.getPreId());
		// ---
		final OActivity firstActivity = getFirtActivityByProcess(processExecution.getProId());
		final OActivityExecution firstActivityExecution = initActivityExecutionWithActivity(firstActivity, processExecution.getPreId());
		activityExecutionDAO.save(firstActivityExecution);

		final ActivityExecutionWorkspace initialWorkspace = new ActivityExecutionWorkspace(processExecution.getProcess().getInitialParams());
		if (!StringUtil.isEmpty(processPlanification.getInitialParams())) {
			// If Plannification specifies initialParams we take them in addition
			initialWorkspace.addExternalParams(processPlanification.getInitialParams());
		}
		// We set in the workspace essentials params
		initialWorkspace.setProcessName(processExecution.getProcess().getName());
		initialWorkspace.setProcessExecutionId(processExecution.getPreId());
		initialWorkspace.setActivityExecutionId(firstActivityExecution.getAceId());
		initialWorkspace.setToken(firstActivityExecution.getToken());
		// ---
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

	private void endActivityExecution(final OActivityExecution activityExecution, final ExecutionState executionState) {
		Assertion.checkNotNull(activityExecution);
		Assertion.checkNotNull(executionState);
		// ---

		switch (executionState) {
			case DONE:
				endActivityExecutionAndInitNext(activityExecution);
				break;
			case ERROR:
				changeExecutionState(activityExecution, ExecutionState.ERROR);
				break;
			case PENDING:
				changeExecutionState(activityExecution, ExecutionState.PENDING);
				break;
			case RUNNING:
			case SUBMITTED:
			case WAITING:
			default:
				throw new RuntimeException("Unknwon case for ending activity execution :  " + executionState.name());
		}

	}

	private void endActivityExecutionAndInitNext(final OActivityExecution activityExecution) {
		OActivityExecution nextActivityExecution = null;
		ActivityExecutionWorkspace nextWorkspace = null;
		boolean hasNext = false;
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			endActivity(activityExecution);

			final Option<OActivity> nextActivity = getNextActivityByActId(activityExecution.getActId());
			if (nextActivity.isDefined()) {
				hasNext = true;
				nextActivityExecution = initActivityExecutionWithActivity(nextActivity.get(), activityExecution.getPreId());
				// We keep the previous worker (Not the same but the slot) for the next Activity Execution
				reserveActivityExecution(nextActivityExecution);
				activityExecutionDAO.save(nextActivityExecution);

				// We keep the old workspace for the nextTask
				final ActivityExecutionWorkspace previousWorkspace = getWorkspaceForActivityExecution(activityExecution.getAceId(), false);
				// We remove the status and update the activityExecutionId and token
				previousWorkspace.resetStatus();
				previousWorkspace.resetLogFile();
				previousWorkspace.setActivityExecutionId(nextActivityExecution.getAceId());
				previousWorkspace.setToken(nextActivityExecution.getToken());
				// ---
				saveActivityExecutionWorkspace(nextActivityExecution.getAceId(), previousWorkspace, true);
				nextActivityExecution.setBeginTime(new Date());
				nextWorkspace = previousWorkspace;

			} else {
				endSuccessfulProcessExecution(activityExecution.getPreId());
			}
			transaction.commit();

		}
		if (hasNext) {
			Assertion.checkNotNull(nextActivityExecution);
			Assertion.checkNotNull(nextWorkspace);
			// ---
			workers.submit(new OWorker(nextActivityExecution, nextWorkspace, this));
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
		final int maxNumber = getUnusedWorkersCount();
		// ---
		executionPAO.reserveActivitiesToLaunch(nodId, maxNumber);
		return activityExecutionDAO.getActivitiesToLaunch(nodId);
	}

	private static OActivityExecution initActivityExecutionWithActivity(final OActivity activity, final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		final OActivityExecution activityExecution = new OActivityExecution();

		activityExecution.setPreId(preId);
		activityExecution.setActId(activity.getActId());
		activityExecution.setCreationTime(new Date());
		activityExecution.setEngine(activity.getEngine());
		activityExecution.setEstCd(ExecutionState.WAITING.name());
		activityExecution.setToken(ActivityTokenGenerator.getToken());

		return activityExecution;

	}

	private void reserveActivityExecution(final OActivityExecution activityExecution) {
		activityExecution.setEstCd(ExecutionState.SUBMITTED.name());
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

	private void saveActivityLogs(final Long aceId, final ActivityLogger activityLogger, final ActivityExecutionWorkspace resultWorkspace) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(activityLogger);
		// ---
		if (transactionManager.hasCurrentTransaction()) {
			doSaveActivityLogs(aceId, activityLogger, resultWorkspace);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doSaveActivityLogs(aceId, activityLogger, resultWorkspace);
				transaction.commit();
			}
		}

	}

	private void doSaveActivityLogs(final Long aceId, final ActivityLogger activityLogger, final ActivityExecutionWorkspace resultWorkspace) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(activityLogger);
		// ---
		// we need at most on log per activityExecution
		final OActivityLog activityLog = activityLogDAO.getActivityLogByAceId(aceId).getOrElse(new OActivityLog());
		activityLog.setAceId(aceId);
		final String log = new StringBuilder(activityLog.getLog() == null ? "" : activityLog.getLog()).append(activityLogger.getLogAsString())//
				.append("ResultWorkspace : ").append(resultWorkspace.getStringForStorage()).append("\n")//
				.toString();
		activityLog.setLog(log);
		if (resultWorkspace.getLogFile() != null) {
			activityLog.setLogFile(resultWorkspace.getLogFile());
		}
		activityLogDAO.save(activityLog);
	}

	private int getUnusedWorkersCount() {
		Assertion.checkNotNull(workers);
		// ---
		if (workers instanceof ThreadPoolExecutor) {
			final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) workers;
			return workersCount - workersPool.getActiveCount();
		}
		return workersCount;
		//return workersCount - activeWorkersCount;
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

	private void handleDeadNodeProcesses() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final Long now = System.currentTimeMillis();
			// We wait two heartbeat to be sure that the node is dead
			final Date maxDate = new Date(now - 2 * timerDelay);
			executionPAO.handleProcessesOfDeadNodes(maxDate);
			transaction.commit();
		}
	}

}
