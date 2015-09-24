package io.vertigo.orchestra.execution;

import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.impl.execution.TaskExecutionWorkspace;

public interface OTaskManager {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public TaskExecutionWorkspace execute(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace);

}
