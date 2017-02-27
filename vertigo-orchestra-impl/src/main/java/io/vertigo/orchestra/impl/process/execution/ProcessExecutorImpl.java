package io.vertigo.orchestra.impl.process.execution;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.process.execution.ExecutionState;
import io.vertigo.orchestra.process.execution.ProcessExecutor;

public final class ProcessExecutorImpl implements ProcessExecutor {
	private final Map<ProcessType, ProcessExecutorPlugin> executorPluginsMap = new EnumMap<>(ProcessType.class);

	public ProcessExecutorImpl(final List<ProcessExecutorPlugin> processExecutorPlugins) {
		Assertion.checkNotNull(processExecutorPlugins);
		// ---
		for (final ProcessExecutorPlugin processExecutorPlugin : processExecutorPlugins) {
			Assertion.checkState(!executorPluginsMap.containsKey(processExecutorPlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}", processExecutorPlugin.getHandledProcessType());
			executorPluginsMap.put(processExecutorPlugin.getHandledProcessType(), processExecutorPlugin);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void execute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(initialParams);
		// ---
		getPluginByType(processDefinition.getProcessType()).execute(processDefinition, initialParams);
	}

	/** {@inheritDoc} */
	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		// Only Supervised can be pending
		getPluginByType(ProcessType.SUPERVISED).endPendingActivityExecution(activityExecutionId, token, state);
	}

	/** {@inheritDoc} */
	@Override
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		// Only Supervised can be pending
		getPluginByType(ProcessType.SUPERVISED).setActivityExecutionPending(activityExecutionId, workspace);
	}

	private ProcessExecutorPlugin getPluginByType(final ProcessType processType) {
		final ProcessExecutorPlugin executorPlugin = executorPluginsMap.get(processType);
		Assertion.checkNotNull(executorPlugin, "No plugin found for managing processType {0}", processType.name());
		return executorPlugin;
	}
}
