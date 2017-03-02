package io.vertigo.orchestra.services.report;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.orchestra.definitions.ProcessDefinition;

public interface ProcessReport {

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
	List<ExecutionSummary> getSummariesByDate(Date minDate, Date maxDate, Optional<String> status);

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
}
