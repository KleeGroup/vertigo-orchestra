package io.vertigo.orchestra.execution;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Manager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.execution.activity.ActivityExecution;
import io.vertigo.orchestra.execution.activity.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.process.ExecutionSummary;
import io.vertigo.orchestra.execution.process.ProcessExecution;

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

	/****************************************************************************************************************/
	/**                                           Report                                                           **/
	/****************************************************************************************************************/

	/**
	 * Retourne la liste des executions d'un processus répondant à des critères triés par ordre chronologique décroissant.
	 * @param processDefinition le processus concerné par la requête
	 * @param status le status des executions à retourner
	 * @param limit le nombre de resultat souhaités
	 * @param offset le rang du premier résultat retourné
	 * @return la liste des éxécutions répondant aux critères
	 */
	List<ProcessExecution> getProcessExecutions(ProcessDefinition processDefinition, String status, Integer limit, Integer offset);

	/**
	 * Retourne le rapport d'execution d'orchestra sur une période.
	 * @param minDate la date de début
	 * @param maxDate la date de fin
	 * @param status permet de filtrer sur un état d'execution (par exemple voir les processus qui ont eu une execution en erreur sur la période.
	 * @return la liste de résumés répondant aux critères
	 */
	List<ExecutionSummary> getSummariesByDate(Date minDate, Date maxDate, String status);

	/**
	 * Retourne le rapport d'execution d'un processus sur une période.
	 * @param processDefinition le nom du processus
	 * @param minDate la date de début
	 * @param maxDate la date de fin
	 * @return le résumé
	 */
	ExecutionSummary getSummaryByDateAndName(ProcessDefinition processDefinition, Date minDate, Date maxDate);

	/**
	 * Retourne une execution de processus.
	 * @param preId l'id de l'execution
	 * @return l'execution
	 */
	ProcessExecution getProcessExecution(Long preId);

	/**
	 * Récupère la liste des activités d'une execution de processus.
	 * @param preId l'id de l'exécution
	 * @return la liste des activités associées
	 */
	List<ActivityExecution> getActivityExecutionsByProcessExecution(Long preId);

	/**
	 * Retourne une execution d'activité par son id.
	 * @param aceId l'id de l'execution d'activité
	 * @return l'activité
	 */
	ActivityExecution getActivityExecution(Long aceId);

	/****************************************************************************************************************/
	/**                                           Log                                                              **/
	/****************************************************************************************************************/

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
