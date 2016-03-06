package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.execution.TaskExecutionWorkspace;

final class OLocalWorker implements Callable<TaskExecutionWorkspace> {

	private static final Logger LOGGER = Logger.getLogger(ProcessExecutionManager.class);

	private final SequentialExecutorPlugin sequentialExecutor;

	private final OTaskExecution taskExecution;
	private final TaskExecutionWorkspace workspace;

	/**
	 * Constructor.
	 */
	OLocalWorker(final SequentialExecutorPlugin sequentialExecutor, final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace) {
		Assertion.checkNotNull(sequentialExecutor);
		Assertion.checkNotNull(taskExecution);
		Assertion.checkNotNull(workspace);
		// -----
		this.taskExecution = taskExecution;
		this.workspace = workspace;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace call() {
		try {
			return sequentialExecutor.execute(taskExecution, workspace);
		} catch (final Exception e) {
			logError(e);
			throw asRuntimeException(e);
		}
	}

	private void logError(final Throwable e) {
		LOGGER.error("Erreur de la tache : " + taskExecution.getEngine(), e);
	}

	private static RuntimeException asRuntimeException(final Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e);
	}

}
