package io.vertigo.orchestra.execution;

/**
 * Interface d'un engine de tâche.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ActivityEngine {

	/**
	 * Réalise l'exécution d'une tache.
	 */
	ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace);

	/**
	 * Post-traitement à effectuer en cas de succès.
	 */
	ActivityExecutionWorkspace successfulPostTreatment(final ActivityExecutionWorkspace workspace);

	/**
	 * Post-traitement à effectuer en cas d'erreur.
	 */
	ActivityExecutionWorkspace errorPostTreatment(final ActivityExecutionWorkspace workspace, final Exception e);

}
