package io.vertigo.orchestra.impl.execution;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskWorkspaceDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.execution.OTaskWorkspace;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;
import io.vertigo.util.StringUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

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
	private OTaskExecutionDAO taskExecutionDAO;
	@Inject
	private OTaskWorkspaceDAO taskWorkspaceDAO;
	@Inject
	private ExecutionPAO executionPAO;

	private final Long workersCount;
	private final String nodeName;
	private final ExecutorService workers;
	private Timer planTimer;
	private final long timerDelay;

	private final ProcessDefinitionManager processDefinitionManager;
	private final ProcessSchedulerManager processSchedulerManager;
	private final VTransactionManager transactionManager;

	@Inject
	public SequentialExecutorPlugin(
			final ProcessDefinitionManager processDefinitionManager,
			final ProcessSchedulerManager processSchedulerManager,
			final VTransactionManager transactionManager,
			@Named("nodeName") final String nodeName,
			@Named("workersCount") final Long workersCount,
			@Named("executionPeriod") final Integer executionPeriod) {
		Assertion.checkNotNull(processDefinitionManager);
		Assertion.checkNotNull(processSchedulerManager);
		Assertion.checkNotNull(transactionManager);
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(workersCount);
		Assertion.checkNotNull(executionPeriod);
		// ---
		Assertion.checkState(workersCount >= 1, "We need at least 1 worker");
		// ---
		this.processDefinitionManager = processDefinitionManager;
		this.processSchedulerManager = processSchedulerManager;
		this.transactionManager = transactionManager;
		this.nodeName = nodeName;
		this.workersCount = (long) workersCount;
		timerDelay = 1000 * executionPeriod;
		workers = Executors.newFixedThreadPool(workersCount.intValue());
	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		planTimer = new Timer("LaunchTaskToDo", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					executeToDo();
				} catch (final Exception e) {
					// We log the error and we continue the timer
					LOGGER.error("Exception launching tasks to executes", e);
				}

			}
		}, timerDelay + timerDelay / 10, timerDelay);
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		planTimer.cancel();
		planTimer.purge();
		workers.shutdown();
	}

	//--------------------------------------------------------------------------------------------------
	//--- Package
	//--------------------------------------------------------------------------------------------------

	/**
	 * TODO : Description de la méthode.
	 * @param taskExecution
	 * @param workspaceOut
	 */
	void putResult(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspaceOut, final Throwable error) {
		Assertion.checkNotNull(workspaceOut);
		Assertion.checkNotNull(workspaceOut.getValue("status"), "Le status est obligatoire dans le résultat");
		// ---
		// 2. We manage the execution workflow
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			if (error != null) {
				error.printStackTrace();
				changeExecutionState(taskExecution, ExecutionState.ERROR);
			} else {
				if (workspaceOut.isSuccess()) {
					endTaskExecutionAndInitNext(taskExecution);
				} else {
					changeExecutionState(taskExecution, ExecutionState.ERROR);
				}
			}
			transaction.commit();
		}

	}

	void changeExecutionState(final OTaskExecution taskExecution, final ExecutionState executionState) {
		Assertion.checkNotNull(taskExecution);
		// ---
		taskExecution.setEstCd(executionState.name());
		taskExecutionDAO.save(taskExecution);

		// If it's an error the entire process is in Error
		if (ExecutionState.ERROR.equals(executionState)) {
			endProcessExecution(taskExecution.getPreId(), ExecutionState.ERROR);
		}

	}

	TaskExecutionWorkspace getWorkspaceForTaskExecution(final Long tkeId, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(in);
		// ---
		final OTaskWorkspace taskWorkspace = taskWorkspaceDAO.getTaskWorkspace(tkeId, in);
		return new TaskExecutionWorkspace(taskWorkspace.getWorkspace());
	}

	void saveTaskExecutionWorkspace(final Long tkeId, final TaskExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(in);
		Assertion.checkNotNull(workspace);
		// ---
		final OTaskWorkspace taskWorkspace = new OTaskWorkspace();
		taskWorkspace.setTkeId(tkeId);
		taskWorkspace.setIsIn(in);
		taskWorkspace.setWorkspace(workspace.getStringForStorage());

		taskWorkspaceDAO.save(taskWorkspace);

	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private void executeToDo() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			initNewProcessesToLaunch();
			transaction.commit();
		}
		final DtList<OTaskExecution> tasksToLaunch;
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			tasksToLaunch = getTasksToLaunch();
			transaction.commit();
		}
		for (final OTaskExecution taskExecution : tasksToLaunch) { //We submit only the process we can handle, no queue
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				final TaskExecutionWorkspace workspace = getWorkspaceForTaskExecution(taskExecution.getTkeId(), true);
				changeExecutionState(taskExecution, ExecutionState.SUBMITTED);
				workers.submit(new OWorker(taskExecution, workspace, this));
				transaction.commit();
			}
		}

	}

	private void initFirstTaskExecution(final OProcessExecution processExecution, final OProcessPlanification processPlanification) {
		Assertion.checkNotNull(processExecution.getProId());
		Assertion.checkNotNull(processExecution.getPreId());
		// ---
		final OTask firstTask = processDefinitionManager.getFirtTaskByProcess(processExecution.getProId());
		final OTaskExecution firstTaskExecution = initTaskExecutionWithTask(firstTask, processExecution.getPreId());
		taskExecutionDAO.save(firstTaskExecution);

		final TaskExecutionWorkspace initialWorkspace;
		if (!StringUtil.isEmpty(processPlanification.getInitialParams())) {
			// If Plannification specifies initialParams we take them
			initialWorkspace = new TaskExecutionWorkspace(processPlanification.getInitialParams());
		} else {
			// Otherwise we take the process initial params for the firstWorkspace
			initialWorkspace = new TaskExecutionWorkspace(processExecution.getProcess().getInitialParams());

		}
		saveTaskExecutionWorkspace(firstTaskExecution.getTkeId(), initialWorkspace, true);

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

	private void endTaskExecutionAndInitNext(final OTaskExecution taskExecution) {
		endTask(taskExecution);

		final Option<OTask> nextTask = processDefinitionManager.getNextTaskByTskId(taskExecution.getTskId());
		if (nextTask.isDefined()) {
			final OTaskExecution nextTaskExecution = initTaskExecutionWithTask(nextTask.get(), taskExecution.getPreId());
			taskExecutionDAO.save(nextTaskExecution);
			// We keep the old workspace for the nextTask
			final TaskExecutionWorkspace previousWorkspace = getWorkspaceForTaskExecution(taskExecution.getTkeId(), false);
			// We remove the status
			previousWorkspace.resetStatus();
			saveTaskExecutionWorkspace(nextTaskExecution.getTkeId(), previousWorkspace, true);

		} else {
			endSuccessfulProcessExecution(taskExecution.getPreId());
		}
	}

	private void initNewProcessesToLaunch() {
		for (final OProcessPlanification processPlanification : processSchedulerManager.getProcessToExecute()) {
			if (canExecute(processPlanification)) {
				final OProcessExecution processExecution = initProcessExecution(processPlanification);
				processSchedulerManager.triggerPlanification(processPlanification);
				initFirstTaskExecution(processExecution, processPlanification);
			} else {
				processSchedulerManager.misfirePlanification(processPlanification);
			}
		}
	}

	private DtList<OTaskExecution> getTasksToLaunch() {
		final Long maxNumber = getUnusedWorkersCount();
		Assertion.checkNotNull(maxNumber);
		// ---
		executionPAO.reserveTasksToLaunch(nodeName, maxNumber);
		return taskExecutionDAO.getTasksToLaunch(nodeName);
	}

	private static OTaskExecution initTaskExecutionWithTask(final OTask task, final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		final OTaskExecution newTaskExecution = new OTaskExecution();

		newTaskExecution.setPreId(preId);
		newTaskExecution.setTskId(task.getTskId());
		newTaskExecution.setBeginTime(new Date());
		newTaskExecution.setEngine(task.getEngine());
		newTaskExecution.setEstCd(ExecutionState.WAITING.name());

		return newTaskExecution;

	}

	private void endTask(final OTaskExecution taskExecution) {
		taskExecution.setEndTime(new Date());
		taskExecution.setEstCd(ExecutionState.DONE.name());
		taskExecutionDAO.save(taskExecution);

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

	private Long getUnusedWorkersCount() {
		Assertion.checkNotNull(workers);
		// ---
		if (workers instanceof ThreadPoolExecutor) {
			final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) workers;
			return workersCount - workersPool.getActiveCount();
		}
		return workersCount;
	}

}