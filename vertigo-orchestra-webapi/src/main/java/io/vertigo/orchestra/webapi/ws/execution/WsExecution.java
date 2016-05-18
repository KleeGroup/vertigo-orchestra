package io.vertigo.orchestra.webapi.ws.execution;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OProcessExecutionUi;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
import io.vertigo.orchestra.webapi.services.ExecutionServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
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
@PathPrefix("/executions/")
public class WsExecution implements WebServices {

	@Inject
	private ExecutionServices executionServices;
	@Inject
	private DefinitionServices definitionServices;

	/**
	 * Get the processExecution by Id
	 */
	@GET("{proId}")
	@AnonymousAccessAllowed
	public DtList<OProcessExecutionUi> getProcessExecutionsByProcessName(@PathParam("proId") final Long proId, @QueryParam("status") final Option<String> status, @QueryParam("limit") final Option<Long> limit, @QueryParam("offset") final Option<Long> offset) {
		return executionServices.getProcessExecutionsByProId(proId, status.getOrElse(""), limit.getOrElse(50L), offset.getOrElse(0L));
	}

	/**
	 * Get the processExecution by Id
	 */
	@GET("processExecution/{preId}")
	@AnonymousAccessAllowed
	public OProcessExecutionUi getProcessExecutionById(@PathParam("preId") final Long preId) {
		return executionServices.getProcessExecutionById(preId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("processExecution/{preId}/activities")
	@AnonymousAccessAllowed
	public DtList<OActivityExecutionUi> getActivityExecutionsByPreId(@PathParam("preId") final Long preId) {
		return executionServices.getActivityExecutionsByPreId(preId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("processExecution/{preId}/logFile")
	@AnonymousAccessAllowed
	public VFile getLogFileByPreId(@PathParam("preId") final Long preId) {
		return executionServices.getLogFileByPreId(preId);
	}

	/**
	 * Update the process properties
	 * @return
	 */
	@POST("{id}/updateTreatment")
	@AnonymousAccessAllowed
	public OProcessExecutionUi updateProcessProperties(@PathParam("id") final Long id, @InnerBodyParam("checked") final Option<Boolean> checked, @InnerBodyParam("checkingComment") final Option<String> checkingComment) {
		executionServices.updateProcessExecutionTreatment(id, checked.getOrElse(null), checkingComment.getOrElse(null));
		return executionServices.getProcessExecutionById(id);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("activityExecution/{aceId}")
	@AnonymousAccessAllowed
	public OActivityExecutionUi getActivityExecutionById(@PathParam("aceId") final Long aceId) {
		return executionServices.getActivityExecutionById(aceId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("activityExecution/{aceId}/logFile")
	@AnonymousAccessAllowed
	public VFile getLogFileByAceId(@PathParam("aceId") final Long aceId) {
		return executionServices.getLogFileByAceId(aceId);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("summary/{proId}")
	@AnonymousAccessAllowed
	public OExecutionSummary getWeekSummaryByProId(@PathParam("proId") final Long proId) {
		final String processName = definitionServices.getProcessDefinitionById(proId).getName();
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		return executionServices.getSummaryByDateAndName(processName, firstDayOfWeek.getTime(), getFirstDayOfNextWeekDate(firstDayOfWeek));
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("summaries")
	@AnonymousAccessAllowed
	public DtList<OExecutionSummary> getWeekSummaries(@QueryParam("status") final String status, @QueryParam("offset") final int offset) {
		// We take the first day of the current week
		final Calendar firstDayOfWeek = getFirstDayOfWeek();
		// We deal with the offset
		firstDayOfWeek.add(Calendar.DAY_OF_YEAR, offset * 7);
		// We make the call with the proper week dates
		return executionServices.getSummariesByDate(firstDayOfWeek.getTime(), getFirstDayOfNextWeekDate(firstDayOfWeek), status);
	}

	private static Date getFirstDayOfNextWeekDate(final Calendar first) {
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
