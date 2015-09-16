package io.vertigo.orchestra.execution;

import java.util.Map;

public abstract class OTaskEngine {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	public abstract Map<String, String> execute(final Map<String, String> params);

}