package io.vertigo.orchestra.webapi.services.impl;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.webapi.dao.summary.SummaryPAO;
import io.vertigo.orchestra.webapi.dao.uiexecutions.UiexecutionsPAO;
import io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.webapi.domain.uiexecutions.OProcessExecutionUi;
import io.vertigo.orchestra.webapi.services.ExecutionServices;

/**
 * Implementation of access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ExecutionServicesImpl implements ExecutionServices {

	@Inject
	private UiexecutionsPAO uiexecutionsPAO;
	@Inject
	private OProcessDAO processDAO;
	@Inject
	private SummaryPAO summaryPAO;

	/** {@inheritDoc} */
	@Override
	public OProcessExecutionUi getProcessExecutionById(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return uiexecutionsPAO.getExecutionByPreId(preId);
	}

	/** {@inheritDoc} */
	@Override
	public OActivityExecutionUi getActivityExecutionById(final Long aceId) {
		Assertion.checkNotNull(aceId);
		// ---
		return uiexecutionsPAO.getActivitiyByAceId(aceId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessExecutionUi> getProcessExecutionsByProId(final Long proId, final String status, final Long limit, final Long offset) {
		Assertion.checkNotNull(proId);
		// ---
		final OProcess process = processDAO.get(proId);
		//TODO : Mettre un offset et un limit
		return uiexecutionsPAO.getExecutionsByProcessName(process.getName(), status, limit, offset);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OActivityExecutionUi> getActivityExecutionsByPreId(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return uiexecutionsPAO.getActivitiesByPreId(preId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OExecutionSummary> getSummariesByDate(final Date minDate, final Date maxDate) {
		return summaryPAO.getExecutionSummariesByDate(minDate, maxDate);
	}

	/** {@inheritDoc} */
	@Override
	public OExecutionSummary getSummaryByDateAndName(final String processName, final Date minDate, final Date maxDate) {
		return summaryPAO.getExecutionSummaryByDateAndName(minDate, maxDate, processName);
	}

}
