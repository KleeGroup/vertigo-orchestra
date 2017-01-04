package io.vertigo.orchestra.webapi.ws.execution;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.execution.activity.ActivityExecution;
import io.vertigo.orchestra.execution.process.ExecutionSummary;
import io.vertigo.orchestra.execution.process.ProcessExecution;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.QueryParam;

/**
 * WebService API for managing Executions
 * @author mlaroche.
 * @version $Id$
 */
@PathPrefix("/orchestra/executions")
public class WsExecution implements WebServices {

	private static final Integer DEFAULT_PAGE_SIZE = 50;
	private static final Integer DEFAULT_OFFSET = 0;

	private static final Integer WEEK_DAYS = 7;

	@Inject
	private ProcessDefinitionManager definitionManager;
	@Inject
	private ProcessExecutionManager executionManager;

	/**
	 * Retourne la liste des executions d'un processus répondant à des critères triés par ordre chronologique décroissant
	 * @param processName l'id du processus concerné par la requête
	 * @param status le status des executions à retourner
	 * @param limit le nombre de resultat souhaités
	 * @param offset le rang du premier résultat retourné
	 * @return la liste des éxécutions répondant aux critères
	 */
	@GET("/{processName}")
	public List<ProcessExecution> getProcessExecutionsByProcessName(@PathParam("processName") final String processName, @QueryParam("status") final Optional<String> status,
			@QueryParam("limit") final Optional<Integer> limit, @QueryParam("offset") final Optional<Integer> offset) {
		final ProcessDefinition processDefinition = definitionManager.getProcessDefinition(processName);
		return executionManager.getReport().getProcessExecutions(processDefinition, status.orElse(""), limit.orElse(DEFAULT_PAGE_SIZE), offset.orElse(DEFAULT_OFFSET));
	}

	/**
	 * Retourne une execution de processus.
	 * @param preId l'id de l'execution
	 * @return l'execution
	 */
	@GET("/processExecution/{preId}")
	public ProcessExecution getProcessExecutionById(@PathParam("preId") final Long preId) {
		return executionManager.getReport().getProcessExecution(preId);
	}

	/**
	 * Récupère la liste des activités d'une execution de processus.
	 * @param preId l'id de l'exécution
	 * @return la liste des activités associées
	 */
	@GET("/processExecution/{preId}/activities")
	public List<ActivityExecution> getActivityExecutionsByPreId(@PathParam("preId") final Long preId) {
		return executionManager.getReport().getActivityExecutionsByProcessExecution(preId);
	}

	/**
	 * Récupère le fichier de log d'une execution de processus
	 * @param preId l'id de l'exécution
	 * @return le fichier de log
	 */
	@GET("/processExecution/{preId}/logFile")
	public VFile getLogFileByPreId(@PathParam("preId") final Long preId) {
		return executionManager.getLogFileForProcess(preId).get();
	}

	/**
	 * Apporte des précession sur un traitement
	 * @param id l'id de l'execution
	 * @param checked si l'execution à été vérifiée
	 * @param checkingComment le commentaire associé
	 * @return l'exécution mise à jour
	 */
	@POST("/updateTreatment")
	public ProcessExecution updateProcessProperties(@InnerBodyParam("id") final Long id, @InnerBodyParam("checked") final Optional<Boolean> checked,
			@InnerBodyParam("checkingComment") final Optional<String> checkingComment) {
		return executionManager.getReport().getProcessExecution(id);
	}

	/**
	 * Retourne une execution d'activité par son id.
	 * @param aceId l'id de l'execution d'activité
	 * @return l'activité
	 */
	@GET("/activityExecution/{aceId}")
	public ActivityExecution getActivityExecutionById(@PathParam("aceId") final Long aceId) {
		return executionManager.getReport().getActivityExecution(aceId);
	}

	/**
	 * Récupère le fichier de log d'une execution d'activité
	 * @param aceId l'id de l'exécution
	 * @return le fichier de log
	 */
	@GET("/activityExecution/{aceId}/logFile")
	public VFile getLogFileByAceId(@PathParam("aceId") final Long aceId) {
		return executionManager.getLogFileForActivity(aceId).get();
	}

	/**
	 * Récupère le fichier de log d'une execution d'activité
	 * @param aceId l'id de l'exécution
	 * @return le fichier de log
	 */
	@GET("/activityExecution/{aceId}/technicalLogFile")
	public VFile getTechnicalLogFileByAceId(@PathParam("aceId") final Long aceId) {
		return executionManager.getTechnicalLogFileForActivity(aceId).get();
	}

	/**
	 * Retourne le rapport d'execution d'un processus sur une période.
	 * @param processName l'id du processus
	 * @return le résumé
	 */
	@GET("/summary/{processName}")
	public ExecutionSummary getWeekSummaryByProcessName(@PathParam("processName") final String processName) {
		final ProcessDefinition processDefinition = definitionManager.getProcessDefinition(processName);
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		return executionManager.getReport().getSummaryByDateAndName(processDefinition, firstDayOfWeek.getTime(), getFirstDayOfNextWeekDate(firstDayOfWeek));
	}

	/**
	 * Retourne le rapport d'execution d'orchestra de la semaine courante.
	 * @param status permet de filtrer sur un état d'execution (par exemple voir les processus qui ont eu une execution en erreur sur la période.
	 * @param offset le décalage de semaine (-1 semaine dernière etc...)
	 * @return la liste de résumés répondant aux critères
	 */
	@GET("/summaries")
	public List<ExecutionSummary> getWeekSummaries(@QueryParam("status") final String status, @QueryParam("offset") final int offset) {
		// We take the first day of the current week
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		// We deal with the offset
		firstDayOfWeek.add(Calendar.DAY_OF_YEAR, offset * WEEK_DAYS);
		// We make the call with the proper week dates
		return executionManager.getReport().getSummariesByDate(firstDayOfWeek.getTime(), getFirstDayOfNextWeekDate(firstDayOfWeek), status);
	}

	private static Date getFirstDayOfNextWeekDate(final Calendar first) {
		// and add seven days to the end date
		final Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, WEEK_DAYS);

		return last.getTime();
	}

	private static Calendar getFirstDayOfWeek() {
		final Calendar cal = new GregorianCalendar(Locale.FRANCE);
		// "calculate" the start date of the week
		final Calendar first = (Calendar) cal.clone();
		first.set(Calendar.DAY_OF_WEEK, first.getFirstDayOfWeek());
		first.set(Calendar.HOUR_OF_DAY, 0);
		first.set(Calendar.MINUTE, 0);
		first.set(Calendar.SECOND, 0);

		first.set(Calendar.MILLISECOND, 0);

		return first;

	}
}
