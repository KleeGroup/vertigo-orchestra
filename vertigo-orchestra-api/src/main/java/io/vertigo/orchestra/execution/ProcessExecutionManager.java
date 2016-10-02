package io.vertigo.orchestra.execution;

import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Manager;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * Interface (interne) de gestion des executions des processus Orchestra.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends Manager {

	/**
	 * Execute un processus.
	 * @param processDefinition le processus à lancer
	 * @param initialParams paramètres initiaux supplémentaires
	 */
	void execute(ProcessDefinition processDefinition, Optional<String> initialParams);

	/**
	 * Termine une execution mise en attente.
	 * @param activityExecutionId L'id de l'execution à terminer
	 * @param token Le ticket associé permettant de s'assurer que n'importe qui ne termine pas une activity (seulement un callback)
	 * @param state L'état futur de l'activité
	 */
	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);

	/**
	 * Mets une execution en attente.
	 * @param activityExecutionId L'id de l'execution à mettre en attente
	 * @param workspace Le workspace avant la mise en attente
	 */
	void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace);

	/**
	 * Récupère le fichier de log d'une execution de processus.
	 * @param processExecutionId L'id de l'execution
	 * @return le fichier de log
	 */
	Optional<VFile> getLogFileForProcess(final Long processExecutionId);

	/**
	 * Récupère le fichier de log d'une execution d'activité.
	 * @param actityExecutionId l'id de l'activité
	 * @return le fichier de log de l'activité
	 */
	Optional<VFile> getLogFileForActivity(Long actityExecutionId);

	/**
	 * Récupère sous forme de fichier le log technique d'une activité. (Si l'activité possède un log)
	 * @param actityExecutionId l'id de l'activité
	 * @return le fichier de log technique de l'activité
	 */
	Optional<VFile> getTechnicalLogFileForActivity(Long actityExecutionId);
}
