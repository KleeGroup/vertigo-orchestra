package io.vertigo.orchestra.impl.execution;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OExecutionWorkspaceDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OExecutionWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

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
	private OExecutionWorkspaceDAO executionWorkspaceDAO;
	@Inject
	private ExecutionPAO executionPAO;

	//--------------------------------------------------------------------------------------------------
	//--- Initialisation
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void postStart(final ProcessExecutionManager processExecutionManager) {
		Assertion.checkNotNull(executionPeriod);
		Assertion.checkNotNull(workersCount);
		Assertion.checkArgNotEmpty(nodeName);
		// ---
		sequentialExecutor = new SequentialExecutor(processExecutionManager, workersCount, executionPeriod * 1000);
		sequentialExecutor.start();

	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public DtList<OTaskExecution> getTasksToLaunch() {
		executionPAO.reserveTasksToLaunch(nodeName);
		return taskExecutionDAO.getTasksToLaunch(nodeName);
	}

	/** {@inheritDoc} */
	@Override
	public void initNewProcessesToLaunch() {
		for (final OProcessPlanification processPlanification : processPlannerManager.getProcessToExecute()) {
			if (canExecute(processPlanification)) {
				final OProcessExecution processExecution = initProcessExecution(processPlanification);
				processPlannerManager.triggerPlanification(processPlanification);
				initFirstTaskExecution(processExecution);
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
		final OExecutionWorkspace executionWorkspace = executionWorkspaceDAO.getExecutionWorkspace(tkeId, in);
		return new TaskExecutionWorkspace(executionWorkspace.getWorkspace());
	}

	/** {@inheritDoc} */
	@Override
	public void saveTaskExecutionWorkspace(final Long tkeId, final TaskExecutionWorkspace workspace, final Boolean in) {
		Assertion.checkNotNull(tkeId);
		Assertion.checkNotNull(in);
		Assertion.checkNotNull(workspace);
		// ---
		final OExecutionWorkspace executionWorkspace = new OExecutionWorkspace();
		executionWorkspace.setTkeId(tkeId);
		executionWorkspace.setIsIn(in);
		executionWorkspace.setWorkspace(workspace.getStringForStorage());

		executionWorkspaceDAO.save(executionWorkspace);

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

	private void initFirstTaskExecution(final OProcessExecution processExecution) {
		Assertion.checkNotNull(processExecution.getProId());
		Assertion.checkNotNull(processExecution.getPreId());
		// ---
		final OTask firstTask = processDefinitionManager.getFirtTaskByProcess(processExecution.getProId());

		final OTaskExecution firstTaskExecution = initTaskExecutionWithTask(firstTask, processExecution.getPreId());
		taskExecutionDAO.save(firstTaskExecution);

		// We take the process initial params for the firstWorkspace
		saveTaskExecutionWorkspace(firstTaskExecution.getTkeId(), new TaskExecutionWorkspace(processExecution.getProcess().getInitialParams()), true);

	}

	private OTaskExecution initTaskExecutionWithTask(final OTask task, final Long preId) {
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
