package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

final class OLocalWorker implements Callable<ActivityExecutionWorkspace> {

	private static final Logger LOGGER = Logger.getLogger(ProcessExecutionManager.class);

	private final SequentialExecutorPlugin sequentialExecutor;

	private final OActivityExecution activityExecution;
	private final ActivityExecutionWorkspace workspace;

	/**
	 * Constructor.
	 */
	OLocalWorker(final SequentialExecutorPlugin sequentialExecutor, final OActivityExecution activityExecution, final ActivityExecutionWorkspace workspace) {
		Assertion.checkNotNull(sequentialExecutor);
		Assertion.checkNotNull(activityExecution);
		Assertion.checkNotNull(workspace);
		// -----
		this.activityExecution = activityExecution;
		this.workspace = workspace;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace call() {
		try {
			return sequentialExecutor.execute(activityExecution, workspace);
		} catch (final Exception e) {
			LOGGER.error("Erreur de la tache : " + activityExecution.getEngine(), e);
			throw asRuntimeException(e);
		}
	}

	private static RuntimeException asRuntimeException(final Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e);
	}

}
