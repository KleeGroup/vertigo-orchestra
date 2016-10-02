package io.vertigo.orchestra.impl.execution;

import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ExecutionState;

public interface ProcessExecutorPlugin extends Plugin {

	void execute(ProcessDefinition processDefinition, Optional<String> initialParams);

	void endPendingActivityExecution(Long activityExecutionId, String token, ExecutionState state);

	void setActivityExecutionPending(Long activityExecutionId, ActivityExecutionWorkspace workspace);

	ProcessType getHandledProcessType();

}
