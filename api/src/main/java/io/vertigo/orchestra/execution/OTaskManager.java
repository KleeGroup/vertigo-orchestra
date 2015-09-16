package io.vertigo.orchestra.execution;

import java.util.Map;

import io.vertigo.orchestra.domain.execution.OTaskExecution;

public interface OTaskManager {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public Map<String, String> execute(final OTaskExecution taskExecution, final Map<String, String> params);

}
