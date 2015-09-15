package io.vertigo.orchestra.process.execution.manager;

import java.util.Map;

import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.services.OrchestraServices;

public interface OTaskManager extends OrchestraServices {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public Map<String, String> execute(final OTaskExecution taskExecution, final Map<String, String> params);

}
