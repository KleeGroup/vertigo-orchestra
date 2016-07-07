package io.vertigo.orchestra.webapi.services.impl;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
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
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private SummaryPAO summaryPAO;
	@Inject
	private ProcessExecutionManager processExecutionManager;

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
	public DtList<OProcessExecutionUi> getProcessExecutionsByProId(final Long proId, final String status, final Integer limit, final Integer offset) {
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
	public DtList<OExecutionSummary> getSummariesByDate(final Date minDate, final Date maxDate, final String status) {
		return summaryPAO.getExecutionSummariesByDate(minDate, maxDate, status);
	}

	/** {@inheritDoc} */
	@Override
	public OExecutionSummary getSummaryByDateAndName(final String processName, final Date minDate, final Date maxDate) {
		return summaryPAO.getExecutionSummaryByDateAndName(minDate, maxDate, processName);
	}

	/** {@inheritDoc} */
	@Override
	public VFile getLogFileByPreId(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return processExecutionManager.getLogFileForProcess(preId).get();
	}

	/** {@inheritDoc} */
	@Override
	public VFile getLogFileByAceId(final Long aceId) {
		Assertion.checkNotNull(aceId);
		// ---
		return processExecutionManager.getLogFileForActivity(aceId).get();
	}

	/** {@inheritDoc} */
	@Override
	public VFile getTechnicalLogFileByAceId(final Long aceId) {
		Assertion.checkNotNull(aceId);
		// ---
		return processExecutionManager.getTechnicalLogFileForActivity(aceId).get();
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessExecutionTreatment(final Long preId, final Boolean checked, final String checkingComment) {
		final OProcessExecution processExecution = processExecutionDAO.get(preId);
		Assertion.checkNotNull(processExecution);
		Assertion.checkState(!ExecutionState.RUNNING.name().equals(processExecution.getEstCd()), "Running execution cannot be checked");
		// ---
		processExecution.setChecked(checked);
		processExecution.setCheckingComment(checkingComment);
		processExecution.setCheckingDate(new Date());
		processExecutionDAO.save(processExecution);
	}

}
