package io.vertigo.orchestra.impl.execution;

import org.apache.log4j.Logger;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;

/**
 * Worker d'une activité orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class OWorker implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(OWorker.class);

	private final OActivityExecution activityExecution;
	private final ActivityExecutionWorkspace params;
	private final SequentialExecutorPlugin sequentialExecutor;

	OWorker(final OActivityExecution activityExecution,
			final ActivityExecutionWorkspace params, final SequentialExecutorPlugin sequentialExecutor) {
		Assertion.checkNotNull(activityExecution);
		// -----
		this.activityExecution = activityExecution;
		this.params = params;
		this.sequentialExecutor = sequentialExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public void run() {
		doRun();
	}

	private void doRun() {
		ActivityExecutionWorkspace result;
		try {
			result = new OLocalWorker(sequentialExecutor, activityExecution, params).call();
			sequentialExecutor.putResult(activityExecution, result, null);
		} catch (final Exception e) {
			LOGGER.info("Error executing activity", e);
			sequentialExecutor.putResult(activityExecution, null, e.getCause());
		}

	}

}
