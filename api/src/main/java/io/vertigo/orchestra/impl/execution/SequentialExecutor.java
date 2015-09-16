package io.vertigo.orchestra.impl.execution;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
final class SequentialExecutor implements Activeable {

	private Timer planTimer;
	private final long timerDelay;

	private final ExecutorService pool = Executors.newCachedThreadPool();

	private final OLocalCoordinator localCoordinator;
	private final ProcessExecutionManager processExecutionManager;

	SequentialExecutor(final ProcessExecutionManager processExecutionManager, final int workersCount, final int timerDelay) {
		Assertion.checkNotNull(processExecutionManager);
		Assertion.checkNotNull(workersCount);
		Assertion.checkNotNull(timerDelay);
		// ---
		this.processExecutionManager = processExecutionManager;
		localCoordinator = new OLocalCoordinator(workersCount);
		this.timerDelay = timerDelay;

	}

	/** {@inheritDoc} */
	@Override
	public void start() {
		planTimer = new Timer("LaunchTaskToDo", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				executeToDo();
			}
		}, timerDelay, timerDelay);

	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		// shutdown >>>>
	}

	private void executeToDo() {
		processExecutionManager.initNewProcessesToLaunch();
		for (final OTaskExecution taskExecution : processExecutionManager.getTasksToLaunch()) {
			final Map<String, String> params = new HashMap<>();
			pool.execute(new OWorker(localCoordinator, taskExecution, params, this));
		}
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
				processExecutionManager.endTaskExecutionAndInitNext(tkeId);
			} else {
				// TODO
			}
		}

	}

}
