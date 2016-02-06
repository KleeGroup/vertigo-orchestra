package io.vertigo.orchestra.execution;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Component;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.impl.execution.TaskExecutionWorkspace;
import io.vertigo.orchestra.impl.execution.TaskLogger;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends Component, Activeable {

	DtList<OTaskExecution> getTasksToLaunch(final Long maxNumber);

	void initNewProcessesToLaunch();

	void endTaskExecutionAndInitNext(OTaskExecution taskExecution);

	void changeExecutionState(OTaskExecution taskExecution, ExecutionState executionState);

	TaskExecutionWorkspace getWorkspaceForTaskExecution(final Long tkeId, final Boolean in);

	void saveTaskExecutionWorkspace(Long tkeId, TaskExecutionWorkspace workspace, Boolean in);

	void saveTaskLogs(Long tkeId, TaskLogger taskLogger);

}
