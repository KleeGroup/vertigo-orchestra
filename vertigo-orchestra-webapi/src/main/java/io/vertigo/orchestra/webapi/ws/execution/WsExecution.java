package io.vertigo.orchestra.webapi.ws.execution;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
import io.vertigo.orchestra.webapi.services.ExecutionServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

/**
 * WebService API for managing Executions
 * @author mlaroche.
 * @version $Id$
 */
@PathPrefix("/executions/")
public class WsExecution implements WebServices {

	@Inject
	private ExecutionServices executionServices;
	@Inject
	private DefinitionServices definitionServices;

	/**
	 * Get the processExecution by Id
	 */
	@GET("/{proId}")
	@AnonymousAccessAllowed
	public DtList<OProcessExecution> getProcessExecutionsByProId(@PathParam("proId") final Long proId) {
		return executionServices.getProcessExecutionsByProId(proId);
	}

	/**
	 * Get the processExecution by Id
	 */
	@GET("processExecution/{preId}")
	@AnonymousAccessAllowed
	public OProcessExecution getProcessExecutionById(@PathParam("preId") final Long preId) {
		return executionServices.getProcessExecutionById(preId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("processExecution/{preId}/activities")
	@AnonymousAccessAllowed
	public DtList<OActivityExecution> getActivityExecutionsByPreId(@PathParam("preId") final Long preId) {
		return executionServices.getActivityExecutionsByPreId(preId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("activityExecution/{aceId}")
	@AnonymousAccessAllowed
	public OActivityExecution getActivityExecutionById(@PathParam("aceId") final Long aceId) {
		return executionServices.getActivityExecutionById(aceId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("summary/{proId}")
	@AnonymousAccessAllowed
	public OExecutionSummary getWeekSummaryByProId(@PathParam("proId") final Long proId) {
		final String processName = definitionServices.getProcessDefinitionById(proId).getName();
		return executionServices.getSummaryByDateAndName(processName, getFirstDayOfWeekDate(), getFirstDayOfNextWeekDate());
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("summaries")
	@AnonymousAccessAllowed
	public DtList<OExecutionSummary> getWeekSummaries() {
		return executionServices.getSummariesByDate(getFirstDayOfWeekDate(), getFirstDayOfNextWeekDate());
	}

	private static Date getFirstDayOfWeekDate() {
		return getFirstDayOfWeek().getTime();
	}

	private static Date getFirstDayOfNextWeekDate() {
		final Calendar first = getFirstDayOfWeek();
		// and add seven days to the end date
		final Calendar last = (Calendar) first.clone();
		last.add(Calendar.DAY_OF_YEAR, 7);

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