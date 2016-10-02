package io.vertigo.orchestra.impl.execution;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Assertion;
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

	private final ProcessExecutorPlugin sequentialExecutorPlugin;
	private final Optional<LogProviderPlugin> logProviderPlugin;

	@Inject
	public ProcessExecutionManagerImpl(final ProcessExecutorPlugin sequentialExecutorPlugin, final Optional<LogProviderPlugin> logProviderPlugin) {
		Assertion.checkNotNull(sequentialExecutorPlugin);
		Assertion.checkNotNull(logProviderPlugin);
		// ---
		this.sequentialExecutorPlugin = sequentialExecutorPlugin;
		this.logProviderPlugin = logProviderPlugin;
	}

	/** {@inheritDoc} */
	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		sequentialExecutorPlugin.endPendingActivityExecution(activityExecutionId, token, state);
	}

	/** {@inheritDoc} */
	@Override
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		sequentialExecutorPlugin.setActivityExecutionPending(activityExecutionId, workspace);
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

}
