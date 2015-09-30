package io.vertigo.orchestra.impl.execution;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ExecutionState;
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
		}, timerDelay + timerDelay / 10, timerDelay);

	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		planTimer.cancel();
		planTimer.purge();
	}

	private void executeToDo() {
		processExecutionManager.initNewProcessesToLaunch();
		for (final OTaskExecution taskExecution : processExecutionManager.getTasksToLaunch()) {
			final TaskExecutionWorkspace workspace = processExecutionManager.getWorkspaceForTaskExecution(taskExecution.getTkeId(), true);
			pool.execute(new OWorker(localCoordinator, taskExecution, workspace, this));
		}
	}

	/**
	 * TODO : Description de la méthode.
	 * @param taskExecution
	 * @param workspaceOut
	 */
	public void putResult(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspaceOut, final Throwable error) {
		Assertion.checkNotNull(workspaceOut);
		Assertion.checkNotNull(workspaceOut.getValue("status"), "Le status est obligatoire dans le résultat");
		// ---
		// 2. We manage the execution workflow
		if (error != null) {
			error.printStackTrace();
			processExecutionManager.changeExecutionState(taskExecution, ExecutionState.ERROR);
		} else {
			if (workspaceOut.isSuccess()) {
				processExecutionManager.endTaskExecutionAndInitNext(taskExecution);
			} else {
				processExecutionManager.changeExecutionState(taskExecution, ExecutionState.ERROR);
			}
		}

	}

}
