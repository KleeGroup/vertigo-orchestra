package io.vertigo.orchestra.impl.process.execution;

import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.process.execution.ExecutionState;

/**
 * Plugin d'execution des processus orchestra.
 * @author mlaroche
 *
 */
public interface ProcessExecutorPlugin extends Plugin {

	/**
	 * @see io.vertigo.orchestra.process.execution.ProcessExecutor#execute(ProcessDefinition, Optional)
	 */
	void execute(ProcessDefinition processDefinition, Optional<String> initialParams);

	/**
	 * @see io.vertigo.orchestra.process.execution.ProcessExecutor#endPendingActivityExecution(Long, String, ExecutionState)
	 */
	void endPendingActivityExecution(Long activityExecutionId, String token, ExecutionState state);

	/**
	 * @see io.vertigo.orchestra.process.execution.ProcessExecutor#setActivityExecutionPending(Long, ActivityExecutionWorkspace)
	 */
	void setActivityExecutionPending(Long activityExecutionId, ActivityExecutionWorkspace workspace);

	/**
	 * Retourne le type de processus géré par le plugin
	 * @return le type de processus géré
	 */
	ProcessType getHandledProcessType();

}
