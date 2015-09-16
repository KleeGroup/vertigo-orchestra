package io.vertigo.orchestra.execution;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.PostActiveable;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends PostActiveable<ProcessExecutionManager> {

	OTaskExecution getTaskExecutionById(final Long tkeId);

	Option<OTask> getNextTaskByProcessExecution(Long preId);

	OProcessExecution getProcessExecutionById(Long preId);

	void saveProcessExecution(OProcessExecution processExecution);

	void saveTaskExecution(OTaskExecution taskExecution);

	DtList<OTaskExecution> getTasksToLaunch();

	void initNewProcessesToLaunch();

	void endProcessExecution(Long proId);

	void initFirstTaskExecution(final Long proId, final Long preId);

	void endTaskExecutionAndInitNext(final Long tkeId);

}
