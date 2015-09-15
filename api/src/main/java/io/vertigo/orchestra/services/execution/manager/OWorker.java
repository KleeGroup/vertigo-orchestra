package io.vertigo.orchestra.services.execution.manager;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.services.execution.plugin.DbSequentialCoordinatorPlugin;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OWorker implements Runnable {

	private final OLocalCoordinator localCoordinator;
	private final OTaskExecution taskExecution;
	private final Map<String, String> params;
	private final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin;

	public OWorker(final OLocalCoordinator localCoordinator, final OTaskExecution taskExecution,
			final Map<String, String> params, final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin) {
		Assertion.checkNotNull(localCoordinator);
		// -----
		this.localCoordinator = localCoordinator;
		this.taskExecution = taskExecution;
		this.params = params;
		this.dbSequentialCoordinatorPlugin = dbSequentialCoordinatorPlugin;
	}

	/** {@inheritDoc} */
	@Override
	public final void run() {
		doRun();
	}

	private <WR, W> void doRun() {
		final Future<Map<String, String>> futureResult = localCoordinator.submit(taskExecution, params);
		Map<String, String> result;
		try {
			result = futureResult.get();
			dbSequentialCoordinatorPlugin.putResult(taskExecution.getTkeId(), result, null);
		} catch (final ExecutionException | InterruptedException e) {
			dbSequentialCoordinatorPlugin.putResult(taskExecution.getTkeId(), null, e.getCause());
		}
	}

}
