package io.vertigo.orchestra.execution;

import java.util.Map;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.PostActiveable;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends PostActiveable<ProcessExecutionManager> {

	DtList<OTaskExecution> getTasksToLaunch();

	void initNewProcessesToLaunch();

	void endTaskExecutionAndInitNext(OTaskExecution taskExecution);

	void changeExecutionState(OTaskExecution taskExecution, ExecutionState executionState);

	Map<String, String> getParamsForTaskExecution(OTaskExecution taskExecution);

}
