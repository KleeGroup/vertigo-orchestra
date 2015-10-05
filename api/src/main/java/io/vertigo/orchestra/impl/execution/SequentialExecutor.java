package io.vertigo.orchestra.impl.execution;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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
	private final Long workersCount;

	private final ExecutorService workers;

	private final ProcessExecutionManager processExecutionManager;

	SequentialExecutor(final ProcessExecutionManager processExecutionManager, final int workersCount, final int timerDelay) {
		Assertion.checkNotNull(processExecutionManager);
		Assertion.checkNotNull(workersCount);
		Assertion.checkNotNull(timerDelay);
		// ---
		Assertion.checkState(workersCount >= 1, "We need at least 1 worker");
		// ---
		workers = Executors.newFixedThreadPool(workersCount);
		this.processExecutionManager = processExecutionManager;
		this.timerDelay = timerDelay;
		this.workersCount = (long) workersCount;

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
		for (final OTaskExecution taskExecution : processExecutionManager.getTasksToLaunch(getUnusedWorkersCount())) { //We submit only the process we can handle, no queue
			final TaskExecutionWorkspace workspace = processExecutionManager.getWorkspaceForTaskExecution(taskExecution.getTkeId(), true);
			processExecutionManager.changeExecutionState(taskExecution, ExecutionState.SUBMITTED);
			workers.submit(new OWorker(taskExecution, workspace, this));
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

	private Long getUnusedWorkersCount() {
		Assertion.checkNotNull(workers);
		// ---
		if (workers instanceof ThreadPoolExecutor) {
			final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) workers;
			return workersCount - workersPool.getActiveCount();
		}
		return workersCount;
	}

}
