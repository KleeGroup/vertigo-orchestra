package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OWorker implements Runnable {

	private final OActivityExecution activityExecution;
	private final ActivityExecutionWorkspace params;
	private final SequentialExecutorPlugin sequentialExecutor;

	public OWorker(final OActivityExecution activityExecution,
			final ActivityExecutionWorkspace params, final SequentialExecutorPlugin sequentialExecutor) {
		Assertion.checkNotNull(activityExecution);
		// -----
		this.activityExecution = activityExecution;
		this.params = params;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public final void run() {
		doRun();
	}

	private void doRun() {
		final ExecutorService localExecutor = Executors.newSingleThreadExecutor();
		final Future<ActivityExecutionWorkspace> futureResult = localExecutor.submit(new OLocalWorker(sequentialExecutor, activityExecution, params));
		ActivityExecutionWorkspace result;
		try {
			result = futureResult.get();
			sequentialExecutor.putResult(activityExecution, result, null);
		} catch (final ExecutionException | RuntimeException | InterruptedException e) {
			sequentialExecutor.putResult(activityExecution, null, e.getCause());
		} finally {
			localExecutor.shutdown();
		}

	}

}