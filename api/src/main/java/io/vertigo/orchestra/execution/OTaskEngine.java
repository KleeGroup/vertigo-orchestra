package io.vertigo.orchestra.execution;

/**
 * Interface d'un engine de tâche.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface OTaskEngine {

	/**
	 * Réalise l'exécution d'une tache.
	 */
			TaskExecutionWorkspace execute(final TaskExecutionWorkspace workspace);

}
