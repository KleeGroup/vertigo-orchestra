package io.vertigo.orchestra.impl.execution;

import java.util.Date;
import java.util.Optional;
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
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OActivityExecutionDAO;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.dao.execution.OActivityWorkspaceDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.domain.execution.OActivityWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ActivityLogger;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.NodeManager;
import io.vertigo.util.ClassUtil;

/**
 * Executeur des processus orchestra sous la forme d'une séquence linéaire d'activités.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class DbSequentialExecutorPlugin implements ProcessExecutorPlugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(DbSequentialExecutorPlugin.class);

	@Inject
	private OProcessExecutionDAO processExecutionDAO;
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
	private final VTransactionManager transactionManager;

	/**
	 * Constructeur.
	 * @param processSchedulerManager le schedulerManager
	 * @param nodeManager le gestionnaire de noeud
	 * @param transactionManager le gestionnaire de transaction
	 * @param nodeName le nom du noeud en cours
	 * @param workersCount le nombre de worker du noeud
	 * @param executionPeriodSeconds le timer du long-polling
	 */
	@Inject
	public DbSequentialExecutorPlugin(
			final NodeManager nodeManager,
			final VTransactionManager transactionManager,
			@Named("nodeName") final String nodeName,
			@Named("workersCount") final int workersCount,
			@Named("executionPeriodSeconds") final int executionPeriodSeconds) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(nodeName);
		// ---
		Assertion.checkState(workersCount >= 1, "We need at least 1 worker");
		// ---
		this.nodeManager = nodeManager;
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

		localScheduledExecutor.scheduleAtFixedRate(() -> {
			try {
				executeToDo();
				nodeManager.updateHeartbeat(nodId);
				handleDeadNodeProcesses();
			} catch (final Exception e) {
				// We log the error and we continue the timer
				LOGGER.error("Exception launching activities to executes", e);
			}

		}, 0, timerDelay, TimeUnit.MILLISECONDS);

	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		localScheduledExecutor.shutdown();
		workers.shutdown();
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.SUPERVISED;
	}

	//--------------------------------------------------------------------------------------------------
	//--- Package
	//--------------------------------------------------------------------------------------------------

	@Override
	public void execute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		// ---
		// We need to be as short as possible for the commit
		if (transactionManager.hasCurrentTransaction()) {
			doExecute(processDefinition, initialParams);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doExecute(processDefinition, initialParams);
				transaction.commit();
			}
		}
	}

	private void doExecute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		final OProcessExecution processExecution = initProcessExecution(processDefinition);
		initFirstAcitvityExecution(processExecution, initialParams);

	}

	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState executionState) {
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
			LOGGER.info("Unknow error ending a pending activity", e);
			endActivityExecution(activityExecution, ExecutionState.ERROR);

		} finally {
			handleOtherServices(activityEngine, activityExecution, workspace);
		}

		// we continue with the standard workflow for ending executions
		endActivityExecution(activityExecution, executionState);

	}

	@Override
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		// We need to be as short as possible for the commit
		if (transactionManager.hasCurrentTransaction()) {
			try (final VTransactionWritable transaction = transactionManager.createAutonomousTransaction()) {
				doSetActivityExecutionPending(activityExecutionId, workspace);
				transaction.commit();
			}
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doSetActivityExecutionPending(activityExecutionId, workspace);
				transaction.commit();
			}
		}

	}

	private void doSetActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		Assertion.checkNotNull(activityExecutionId);
		// ---
		final OActivityExecution activityExecution = activityExecutionDAO.get(activityExecutionId);
		endActivityExecution(activityExecution, ExecutionState.PENDING);
		saveActivityExecutionWorkspace(activityExecutionId, workspace, false);
	}
	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private void executeToDo() {
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
			workers.submit(() -> {
				doRunActivity(activityExecution, workspace);
			});
		}

	}

	private void doRunActivity(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspace) {
		ActivityExecutionWorkspace result;
		try {
			result = execute(activityExecution, workspace);
			putResult(activityExecution, result, null);
		} catch (final Exception e) {
			LOGGER.info("Error executing activity", e);
			putResult(activityExecution, null, e.getCause());
		}
	}

	private ActivityExecutionWorkspace execute(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspace) {
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
				LOGGER.error("Erreur de l'activité : " + activityExecution.getEngine(), e);
				// we call the posttreament
				resultWorkspace = activityEngine.errorPostTreatment(resultWorkspace, e);

			} finally {
				handleOtherServices(activityEngine, activityExecution, resultWorkspace);
			}

		} catch (final Exception e) {
			// Informative log
			resultWorkspace.setFailure();
			LOGGER.error("Erreur de l'activité : " + activityExecution.getEngine(), e);
		}

		return resultWorkspace;
	}

	private void putResult(final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspaceOut, final Throwable error) {
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

	private void changeExecutionState(final OActivityExecution activityExecution, final ExecutionState executionState) {
		if (transactionManager.hasCurrentTransaction()) {
			doChangeExecutionState(activityExecution, executionState);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doChangeExecutionState(activityExecution, executionState);
				transaction.commit();
			}
		}
	}

	private OProcessExecution initProcessExecution(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		final OProcessExecution newProcessExecution = new OProcessExecution();
		newProcessExecution.setProId(processDefinition.getId());
		newProcessExecution.setBeginTime(new Date());
		newProcessExecution.setEstCd(ExecutionState.RUNNING.name());

		processExecutionDAO.save(newProcessExecution);

		return newProcessExecution;
	}

	private DtList<OActivityExecution> getActivitiesToLaunch() {
		final int maxNumber = getUnusedWorkersCount();
		// ---
		executionPAO.reserveActivitiesToLaunch(nodId, maxNumber);
		return activityExecutionDAO.getActivitiesToLaunch(nodId);
	}

	private void initFirstAcitvityExecution(final OProcessExecution processExecution, final Optional<String> initialParams) {
		Assertion.checkNotNull(processExecution.getProId());
		Assertion.checkNotNull(processExecution.getPreId());
		// ---
		final OActivity firstActivity = activityDAO.getFirstActivityByProcess(processExecution.getProId());
		final OActivityExecution firstActivityExecution = initActivityExecutionWithActivity(firstActivity, processExecution.getPreId());
		activityExecutionDAO.save(firstActivityExecution);

		final ActivityExecutionWorkspace initialWorkspace = new ActivityExecutionWorkspace(processExecution.getProcess().getInitialParams());
		if (initialParams.isPresent()) {
			// If Plannification specifies initialParams we take them in addition
			initialWorkspace.addExternalParams(initialParams.get());
		}
		// We set in the workspace essentials params
		initialWorkspace.setProcessName(processExecution.getProcess().getName());
		initialWorkspace.setProcessExecutionId(processExecution.getPreId());
		initialWorkspace.setActivityExecutionId(firstActivityExecution.getAceId());
		initialWorkspace.setToken(firstActivityExecution.getToken());
		// ---
		saveActivityExecutionWorkspace(firstActivityExecution.getAceId(), initialWorkspace, true);

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

	private void endActivityExecutionAndInitNext(final OActivityExecution activityExecution) {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			endActivity(activityExecution);

			final Optional<OActivity> nextActivity = activityDAO.getNextActivityByActId(activityExecution.getActId());
			if (nextActivity.isPresent()) {
				final OActivityExecution nextActivityExecution;
				final ActivityExecutionWorkspace nextWorkspace;
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
				workers.submit(() -> {
					doRunActivity(nextActivityExecution, nextWorkspace);
				});

			} else {
				endProcessExecution(activityExecution.getPreId(), ExecutionState.DONE);
			}
			transaction.commit();

		}
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
				throw new IllegalArgumentException("Unknwon case for ending activity execution :  " + executionState.name());
		}

	}

	private ActivityExecutionWorkspace getWorkspaceForActivityExecution(final Long aceId, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		// ---
		final OActivityWorkspace activityWorkspace = activityWorkspaceDAO.getActivityWorkspace(aceId, in).get();
		return new ActivityExecutionWorkspace(activityWorkspace.getWorkspace());
	}

	private void saveActivityExecutionWorkspace(final Long aceId, final ActivityExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(in);
		Assertion.checkNotNull(workspace);
		// ---
		// we need at most one workspace in and one workspace out
		final OActivityWorkspace activityWorkspace = activityWorkspaceDAO.getActivityWorkspace(aceId, in).orElse(new OActivityWorkspace());
		activityWorkspace.setAceId(aceId);
		activityWorkspace.setIsIn(in);
		activityWorkspace.setWorkspace(workspace.getStringForStorage());

		activityWorkspaceDAO.save(activityWorkspace);

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

	private void endActivity(final OActivityExecution activityExecution) {
		activityExecution.setEndTime(new Date());
		activityExecution.setEstCd(ExecutionState.DONE.name());
		activityExecutionDAO.save(activityExecution);

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

	private void endProcessExecution(final Long preId, final ExecutionState executionState) {
		final OProcessExecution processExecution = processExecutionDAO.get(preId);
		processExecution.setEndTime(new Date());
		processExecution.setEstCd(executionState.name());
		processExecutionDAO.save(processExecution);

	}

	private void saveActivityLogs(final Long aceId, final ActivityLogger activityLogger, final ActivityExecutionWorkspace resultWorkspace) {
		Assertion.checkNotNull(aceId);
		Assertion.checkNotNull(activityLogger);
		// ---
		// we need at most on log per activityExecution
		final OActivityLog activityLog = activityLogDAO.getActivityLogByAceId(aceId).orElse(new OActivityLog());
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
