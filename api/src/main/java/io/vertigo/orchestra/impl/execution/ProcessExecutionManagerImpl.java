package io.vertigo.orchestra.impl.execution;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskLogDAO;
import io.vertigo.orchestra.dao.execution.OTaskWorkspaceDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.execution.OTaskLog;
import io.vertigo.orchestra.domain.execution.OTaskWorkspace;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.util.StringUtil;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	@Inject
	@Named("nodeName")
	private String nodeName;
	@Inject
	@Named("workersCount")
	private Integer workersCount;
	@Inject
	@Named("executionPeriod")
	private Integer executionPeriod;

	private SequentialExecutor sequentialExecutor;
	@Inject
	private ProcessPlannerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OTaskExecutionDAO taskExecutionDAO;
	@Inject
	private OTaskWorkspaceDAO taskWorkspaceDAO;
	@Inject
	private OTaskLogDAO taskLogDAO;
	@Inject
	private ExecutionPAO executionPAO;

	//--------------------------------------------------------------------------------------------------
	//--- Activation
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void start() {
		Assertion.checkNotNull(executionPeriod);
		Assertion.checkNotNull(workersCount);
		Assertion.checkArgNotEmpty(nodeName);
		// ---
		sequentialExecutor = new SequentialExecutor(this, workersCount, executionPeriod * 1000);
		sequentialExecutor.start();
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		sequentialExecutor.stop();

	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public DtList<OTaskExecution> getTasksToLaunch(final Long maxNumber) {
		Assertion.checkNotNull(maxNumber);
		// ---
		executionPAO.reserveTasksToLaunch(nodeName, maxNumber);
		return taskExecutionDAO.getTasksToLaunch(nodeName);
	}

	/** {@inheritDoc} */
	@Override
	public void initNewProcessesToLaunch() {
		for (final OProcessPlanification processPlanification : processPlannerManager.getProcessToExecute()) {
			if (canExecute(processPlanification)) {
				final OProcessExecution processExecution = initProcessExecution(processPlanification);
				processPlannerManager.triggerPlanification(processPlanification);
				initFirstTaskExecution(processExecution, processPlanification);
			} else {
				processPlannerManager.misfirePlanification(processPlanification);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void changeExecutionState(final OTaskExecution taskExecution, final ExecutionState executionState) {
		Assertion.checkNotNull(taskExecution);
		// ---
		taskExecution.setEstCd(executionState.name());
		taskExecutionDAO.save(taskExecution);

		// If it's an error the entire process is in Error
		if (ExecutionState.ERROR.equals(executionState)) {
			endProcessExecution(taskExecution.getPreId(), ExecutionState.ERROR);
		}

	}

	/** {@inheritDoc} */
	@Override
	public void endTaskExecutionAndInitNext(final OTaskExecution taskExecution) {
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

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace getWorkspaceForTaskExecution(final Long tkeId, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(in);
		// ---
		final OTaskWorkspace taskWorkspace = taskWorkspaceDAO.getTaskWorkspace(tkeId, in);
		return new TaskExecutionWorkspace(taskWorkspace.getWorkspace());
	}

	/** {@inheritDoc} */
	@Override
	public void saveTaskExecutionWorkspace(final Long tkeId, final TaskExecutionWorkspace workspace, final Boolean in) {
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

	/** {@inheritDoc} */
	@Override
	public void saveTaskLogs(final Long tkeId, final TaskLogger taskLogger) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(taskLogger);
		//
		final OTaskLog taskLog = new OTaskLog();
		taskLog.setTkeId(tkeId);
		taskLog.setLog(taskLogger.getLogAsString());
		taskLogDAO.save(taskLog);
	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private OProcessExecution initProcessExecution(final OProcessPlanification processPlanification) {
		final OProcess process = processPlanification.getProcessus();
		final OProcessExecution newProcessExecution = new OProcessExecution();
		newProcessExecution.setProId(process.getProId());
		newProcessExecution.setBeginTime(new Date());
		newProcessExecution.setEstCd(ExecutionState.RUNNING.name());

		saveProcessExecution(newProcessExecution);

		return newProcessExecution;
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

}
