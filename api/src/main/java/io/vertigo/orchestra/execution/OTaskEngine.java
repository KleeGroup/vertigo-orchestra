package io.vertigo.orchestra.execution;

import java.util.Map;

public interface OTaskEngine {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	Map<String, String> execute(final Map<String, String> params);

}
