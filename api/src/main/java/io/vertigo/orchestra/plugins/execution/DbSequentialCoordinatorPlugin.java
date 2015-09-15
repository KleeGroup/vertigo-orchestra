package io.vertigo.orchestra.plugins.execution;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.process.execution.manager.OLocalCoordinator;
import io.vertigo.orchestra.process.execution.manager.OWorker;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DbSequentialCoordinatorPlugin implements Plugin, Activeable {

	private Timer planTimer;
	private final long timerDelay = 10 * 1000;

	private final ExecutorService pool = Executors.newCachedThreadPool();

	@Inject
	private ProcessExecutionManager executionServices;

	private final OLocalCoordinator localCoordinator = new OLocalCoordinator(/* workersCount */3);

	/** {@inheritDoc} */
	@Override
	public void start() {
		planTimer = new Timer("LaunchTaskToDo", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				executionServices.initNewProcessesToLaunch();
				for (final OTaskExecution taskExecution : executionServices.getTasksToLaunch()) {
					final Map<String, String> params = new HashMap<>();
					pool.execute(new OWorker(localCoordinator, taskExecution, params, getPlugin()));
				}
			}
		}, timerDelay, timerDelay);

	}

	private DbSequentialCoordinatorPlugin getPlugin() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		// TODO

	}

	/**
	 * TODO : Description de la méthode.
	 *
	 * @param tkeId
	 * @param result
	 */
	public void putResult(final Long tkeId, final Map<String, String> result, final Throwable error) {
		Assertion.checkNotNull(result);
		Assertion.checkArgument(result.containsKey("status"), "Le status est obligatoire dans le résultat");
		// ---
		if (error != null) {
			error.printStackTrace();
		} else {
			if ("ok".equals(result.get("status"))) {
				executionServices.endTaskExecutionAndInitNext(tkeId);
			} else {
				// TODO
			}
		}

	}

}
