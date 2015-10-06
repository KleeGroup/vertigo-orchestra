package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

	private final OTaskExecution taskExecution;
	private final TaskExecutionWorkspace params;
	private final SequentialExecutor sequentialExecutor;

	public OWorker(final OTaskExecution taskExecution,
			final TaskExecutionWorkspace params, final SequentialExecutor sequentialExecutor) {
		Assertion.checkNotNull(taskExecution);
		// -----
		this.taskExecution = taskExecution;
		this.params = params;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public final void run() {
		doRun();
	}

	private  void doRun() {
		final ExecutorService localExecutor = Executors.newSingleThreadExecutor();
		final Future<TaskExecutionWorkspace> futureResult = localExecutor.submit(new OLocalWorker(taskExecution, params));
		TaskExecutionWorkspace result;
		try {
			result = futureResult.get();
			sequentialExecutor.putResult(taskExecution, result, null);
		} catch (final ExecutionException | RuntimeException | InterruptedException e) {
			sequentialExecutor.putResult(taskExecution, null, e.getCause());
		} finally {
			localExecutor.shutdown();
		}

	}

}
