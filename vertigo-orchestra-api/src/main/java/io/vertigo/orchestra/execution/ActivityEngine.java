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
	 * @param workspace le workspace d'entrée
	 * @return le workspace de sortie
	 */
	ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace);

	/**
	 * Post-traitement à effectuer en cas de succès.
	 * @param workspace le workspace d'entrée
	 * @return le workspace de sortie
	 */
	ActivityExecutionWorkspace successfulPostTreatment(final ActivityExecutionWorkspace workspace);

	/**
	 * Post-traitement à effectuer en cas d'erreur.
	 * @param workspace le workspace d'entrée
	 * @param e l'exception rencontrée lors du traitement
	 * @return le workspace de sortie
	 */
	ActivityExecutionWorkspace errorPostTreatment(final ActivityExecutionWorkspace workspace, final Exception e);

}
