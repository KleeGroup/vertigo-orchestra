package io.vertigo.orchestra.impl.execution;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.ExecutionPAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

import java.util.Date;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	@Inject
	private ProcessPlannerManager processPlanificationServices;
	@Inject
	private ProcessDefinitionManager processServices;
	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OTaskExecutionDAO taskExecutionDAO;
	@Inject
	private ExecutionPAO executionPAO;

	//	@Inject
	//	private OTaskDAO taskDAO;

	/** {@inheritDoc} */
	@Override
	public OTaskExecution getTaskExecutionById(final Long tkeId) {
		return taskExecutionDAO.get(tkeId);
	}

	/** {@inheritDoc} */
	@Override
	public Option<OTask> getNextTaskByProcessExecution(final Long preId) {
		return Option.<OTask> none();
	}

	/** {@inheritDoc} */
	@Override
	public OProcessExecution getProcessExecutionById(final Long preId) {
		return processExecutionDAO.get(preId);
	}

	/** {@inheritDoc} */
	@Override
	public void saveProcessExecution(final OProcessExecution processExecution) {
		processExecutionDAO.save(processExecution);

	}

	/** {@inheritDoc} */
	@Override
	public void saveTaskExecution(final OTaskExecution taskExecution) {
		taskExecutionDAO.save(taskExecution);

	}

	/** {@inheritDoc} */
	@Override
	public OProcessExecution getLastProcessExecution(final Long proId) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessExecution> getProcessExecutionInScope(final Long proId) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OTaskExecution> getTasksToLaunch() {
		executionPAO.reserveTasksToLaunch();
		return taskExecutionDAO.getTasksToLaunch();
	}

	/** {@inheritDoc} */
	@Override
	public void initNewProcessesToLaunch() {
		final DtList<OProcessPlanification> processToExecute = processPlanificationServices.getProcessToExecute();

		for (final OProcessPlanification processPlanification : processToExecute) {
			final OProcessExecution newProcessExecution = new OProcessExecution();
			final OProcess process = processPlanification.getProcessus();

			newProcessExecution.setProId(process.getProId());
			newProcessExecution.setBeginTime(new Date());
			newProcessExecution.setEstCd("RUNNING");

			saveProcessExecution(newProcessExecution);

			processPlanificationServices.triggerPlanification(processPlanification);

			initFirstTaskExecution(process.getProId(), newProcessExecution.getPreId());

		}

	}

	/** {@inheritDoc} */
	@Override
	public void endProcessExecution(final Long preId) {
		final OProcessExecution processExecution = getProcessExecutionById(preId);
		processExecution.setEndTime(new Date());
		processExecution.setEstCd("DONE");
		saveProcessExecution(processExecution);

	}

	/** {@inheritDoc} */
	@Override
	public void initFirstTaskExecution(final Long proId, final Long preId) {
		final OTask firstTask = processServices.getFirtTaskByProcess(proId);
		taskExecutionDAO.save(initTaskExecutionWithTask(firstTask, preId));

	}

	/** {@inheritDoc} */
	@Override
	public void endTaskExecutionAndInitNext(final Long tkeId) {
		final OTaskExecution taskExecution = getTaskExecutionById(tkeId);
		endTask(taskExecution);

		final Option<OTask> nextTask = getNextTaskByProcessExecution(taskExecution.getPreId());
		if (nextTask.isDefined()) {
			taskExecutionDAO.save(initTaskExecutionWithTask(nextTask.get(), taskExecution.getPreId()));
		} else {
			endProcessExecution(taskExecution.getPreId());
		}
	}

	private OTaskExecution initTaskExecutionWithTask(final OTask task, final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		final OTaskExecution newTaskExecution = new OTaskExecution();

		newTaskExecution.setPreId(preId);
		newTaskExecution.setTskId(task.getTskId());
		newTaskExecution.setBeginTime(new Date());
		newTaskExecution.setEngine(task.getEngine());
		newTaskExecution.setEstCd("WAITING"); // TODO modifier

		return newTaskExecution;

	}

	private void endTask(final OTaskExecution taskExecution) {
		taskExecution.setEndTime(new Date());
		taskExecution.setEstCd("DONE"); // TODO modifier
		taskExecutionDAO.save(taskExecution);

	}

}
