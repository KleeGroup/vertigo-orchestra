package io.vertigo.orchestra.ui.services.impl;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.execution.OActivityExecutionDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.ui.dao.summary.SummaryPAO;
import io.vertigo.orchestra.ui.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.ui.services.ExecutionServices;

/**
 * Implementation of access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ExecutionServicesImpl implements ExecutionServices {

	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OActivityExecutionDAO activityExecutionDAO;
	@Inject
	private SummaryPAO summaryPAO;

	/** {@inheritDoc} */
	@Override
	public OProcessExecution getProcessExecutionById(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return processExecutionDAO.get(preId);
	}

	/** {@inheritDoc} */
	@Override
	public OActivityExecution getActivityExecutionById(final Long aceId) {
		Assertion.checkNotNull(aceId);
		// ---
		return activityExecutionDAO.get(aceId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessExecution> getProcessExecutionsByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		//TODO : Mettre un offset et un limit
		return processExecutionDAO.getExecutionsByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OActivityExecution> getActivityExecutionsByPreId(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return activityExecutionDAO.getActivityExecutionsByPreId(preId);
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
