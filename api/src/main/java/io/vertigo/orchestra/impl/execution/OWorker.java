package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OWorker implements Runnable {

	private final OLocalCoordinator localCoordinator;
	private final OTaskExecution taskExecution;
	private final TaskExecutionWorkspace params;
	private final SequentialExecutor sequentialExecutor;

	public OWorker(final OLocalCoordinator localCoordinator, final OTaskExecution taskExecution,
			final TaskExecutionWorkspace params, final SequentialExecutor sequentialExecutor) {
		Assertion.checkNotNull(localCoordinator);
		// -----
		this.localCoordinator = localCoordinator;
		this.taskExecution = taskExecution;
		this.params = params;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public final void run() {
		doRun();
	}

	private <WR, W> void doRun() {
		final Future<TaskExecutionWorkspace> futureResult = localCoordinator.submit(taskExecution, params);
		TaskExecutionWorkspace result;
		try {
			result = futureResult.get();
			sequentialExecutor.putResult(taskExecution, result, null);
		} catch (final ExecutionException | RuntimeException | InterruptedException e) {
			sequentialExecutor.putResult(taskExecution, null, e.getCause());
		}
	}

}
