package io.vertigo.orchestra.impl.execution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * Implémentation du manager des executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	private final Map<ProcessType, ProcessExecutorPlugin> executorPluginsMap = new HashMap<>();
	private final Optional<LogProviderPlugin> logProviderPlugin;

	@Inject
	public ProcessExecutionManagerImpl(final List<ProcessExecutorPlugin> executorPlugins, final Optional<LogProviderPlugin> logProviderPlugin) {
		Assertion.checkNotNull(logProviderPlugin);
		// ---
		for (final ProcessExecutorPlugin executorPlugin : executorPlugins) {
			Assertion.checkState(!executorPluginsMap.containsKey(executorPlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}", executorPlugin.getHandledProcessType());
			executorPluginsMap.put(executorPlugin.getHandledProcessType(), executorPlugin);
		}
		this.logProviderPlugin = logProviderPlugin;
	}

	/** {@inheritDoc} */
	@Override
	public void execute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		Assertion.checkNotNull(processDefinition);
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

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getLogFileForProcess(final Long processExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getLogFileForProcess(processExecutionId);
		}
		return Optional.<VFile> empty();
	}

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getLogFileForActivity(final Long actityExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getLogFileForActivity(actityExecutionId);
		}
		return Optional.<VFile> empty();
	}

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getTechnicalLogFileForActivity(final Long actityExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getTechnicalLogFileForActivity(actityExecutionId);
		}
		return Optional.<VFile> empty();
	}

	private ProcessExecutorPlugin getPluginByType(final ProcessType processType) {
		final ProcessExecutorPlugin executorPlugin = executorPluginsMap.get(processType);
		Assertion.checkNotNull(executorPlugin, "No plugin found for managing processType {0}", processType.name());
		return executorPlugin;
	}

}
