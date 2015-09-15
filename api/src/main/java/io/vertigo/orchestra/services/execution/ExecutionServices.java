package io.vertigo.orchestra.services.execution;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ExecutionServices extends OrchestraServices {

	public OTaskExecution getTaskExecutionById(final Long tkeId);

	public Option<OTask> getNextTaskByProcessExecution(Long preId);

	public OProcessExecution getProcessExecutionById(Long preId);

	public void saveProcessExecution(OProcessExecution processExecution);

	public void saveTaskExecution(OTaskExecution taskExecution);

	public OProcessExecution getLastProcessExecution(final Long proId);

	public DtList<OProcessExecution> getProcessExecutionInScope(final Long proId);

	public DtList<OTaskExecution> getTasksToLaunch();

	public void initNewProcessesToLaunch();

	public void endProcessExecution(Long proId);

	public void initFirstTaskExecution(final Long proId, final Long preId);

	public void endTaskExecutionAndInitNext(final Long tkeId);

}
