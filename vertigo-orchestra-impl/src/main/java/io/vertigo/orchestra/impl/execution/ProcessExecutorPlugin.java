package io.vertigo.orchestra.impl.execution;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ExecutionState;

public interface ProcessExecutorPlugin extends Plugin {

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);

	void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace);

}
