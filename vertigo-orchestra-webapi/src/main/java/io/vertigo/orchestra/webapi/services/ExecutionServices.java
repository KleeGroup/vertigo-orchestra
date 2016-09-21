package io.vertigo.orchestra.webapi.services;

import java.util.Date;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OProcessExecutionUi;

/**
 * Service for access to orchestra process executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ExecutionServices extends StoreServices {

	/**
	 * Retourne une execution de processus.
	 * @param preId l'id de l'execution
	 * @return l'execution
	 */
	OProcessExecutionUi getProcessExecutionById(Long preId);

	/**
	 * Retourne une execution d'activité par son id.
	 * @param aceId l'id de l'execution d'activité
	 * @return l'activité
	 */
	OActivityExecutionUi getActivityExecutionById(Long aceId);

	/**
	 * Retourne la liste des executions d'un processus répondant à des critères triés par ordre chronologique décroissant
	 * @param proId l'id du processus concerné par la requête
	 * @param status le status des executions à retourner
	 * @param limit le nombre de resultat souhaités
	 * @param offset le rang du premier résultat retourné
	 * @return la liste des éxécutions répondant aux critères
	 */
	DtList<OProcessExecutionUi> getProcessExecutionsByProId(Long proId, String status, Integer limit, Integer offset);

	/**
	 * Récupère la liste des activités d'une execution de processus.
	 * @param preId l'id de l'exécution
	 * @return la liste des activités associées
	 */
	DtList<OActivityExecutionUi> getActivityExecutionsByPreId(Long preId);

	/**
	 * Retourne le rapport d'execution d'orchestra sur une période.
	 * @param minDate la date de début
	 * @param maxDate la date de fin
	 * @param status permet de filtrer sur un état d'execution (par exemple voir les processus qui ont eu une execution en erreur sur la période.
	 * @return la liste de résumés répondant aux critères
	 */
	DtList<OExecutionSummary> getSummariesByDate(Date minDate, Date maxDate, String status);

	/**
	 * Retourne le rapport d'execution d'un processus sur une période.
	 * @param processName le nom du processus
	 * @param minDate la date de début
	 * @param maxDate la date de fin
	 * @return le résumé
	 */
	OExecutionSummary getSummaryByDateAndName(String processName, Date minDate, Date maxDate);

	/**
	 * Récupère le fichier de log d'une execution de processus
	 * @param preId l'id de l'exécution
	 * @return le fichier de log
	 */
	VFile getLogFileByPreId(Long preId);

	/**
	 * Récupère le fichier de log d'une execution d'activité
	 * @param aceId l'id de l'exécution
	 * @return le fichier de log
	 */
	VFile getLogFileByAceId(Long aceId);

	/**
	 * Récupère le fichier de log technique d'une execution d'activité
	 * @param aceId l'id de l'exécution
	 * @return le fichier de log
	 */
	VFile getTechnicalLogFileByAceId(Long aceId);

	/**
	 * Apporte des précession sur un traitement
	 * @param preId l'id de l'execution
	 * @param checked si l'execution à été vérifiée
	 * @param checkingComment le commentaire associé
	 */
	void updateProcessExecutionTreatment(Long preId, Boolean checked, String checkingComment);

}
