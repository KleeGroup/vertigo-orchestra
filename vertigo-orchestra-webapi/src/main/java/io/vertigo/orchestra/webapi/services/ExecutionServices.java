package io.vertigo.orchestra.webapi.services;

import java.util.Date;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary;

/**
 * Service for access to orchestra process executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ExecutionServices extends StoreServices {

	OProcessExecution getProcessExecutionById(Long preId);

	OActivityExecution getActivityExecutionById(Long aceId);

	DtList<OProcessExecution> getProcessExecutionsByProId(Long proId);

	DtList<OActivityExecution> getActivityExecutionsByPreId(Long preId);

	DtList<OExecutionSummary> getSummariesByDate(Date minDate, Date maxDate);

	OExecutionSummary getSummaryByDateAndName(String processName, Date minDate, Date maxDate);

}
