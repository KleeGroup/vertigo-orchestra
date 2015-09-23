package io.vertigo.orchestra.execution;

import io.vertigo.orchestra.domain.execution.OTaskExecution;

public interface OTaskManager {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public TaskExecutionWorkspace execute(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace);

}
