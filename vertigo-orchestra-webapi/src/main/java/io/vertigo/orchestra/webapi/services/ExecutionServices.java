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

	OProcessExecutionUi getProcessExecutionById(Long preId);

	OActivityExecutionUi getActivityExecutionById(Long aceId);

	DtList<OProcessExecutionUi> getProcessExecutionsByProId(Long proId, String status, Integer limit, Integer offset);

	DtList<OActivityExecutionUi> getActivityExecutionsByPreId(Long preId);

	DtList<OExecutionSummary> getSummariesByDate(Date minDate, Date maxDate, String status);

	OExecutionSummary getSummaryByDateAndName(String processName, Date minDate, Date maxDate);

	VFile getLogFileByPreId(Long preId);

	VFile getLogFileByAceId(Long aceId);

	VFile getTechnicalLogFileByAceId(Long aceId);

	void updateProcessExecutionTreatment(Long preId, Boolean checked, String checkingComment);

}
