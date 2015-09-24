package io.vertigo.orchestra.impl.execution;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.vertigo.dynamo.impl.work.worker.Coordinator;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

/**
 * Implémentation d'un pool local de {@link Coordinator}.
 *
 * @author pchretien
 */
public final class OLocalCoordinator {

	/** Pool de workers qui wrappent sur l'implémentation générique. */
	private final ExecutorService workers;

	/**
	 * Constructeur.
	 *
	 * @param workerCount paramètres d'initialisation du pool
	 */
	public OLocalCoordinator(final int workerCount) {
		Assertion.checkArgument(workerCount >= 1, "At least one thread must be allowed to process asynchronous jobs.");
		// -----
		workers = Executors.newFixedThreadPool(workerCount);
	}

	/**
	 * Work devant être exécuté
	 * WorkItem contient à la fois le Work et le callback.
	 *
	 * @param workItem WorkItem
	 */
	public Future<TaskExecutionWorkspace> submit(final OTaskExecution taskExecution, final TaskExecutionWorkspace params) {
		Assertion.checkNotNull(taskExecution);
		// -----
		return workers.submit(new OLocalWorker(taskExecution, params));
	}
}
