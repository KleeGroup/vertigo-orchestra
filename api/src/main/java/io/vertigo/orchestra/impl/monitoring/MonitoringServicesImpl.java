package io.vertigo.orchestra.impl.monitoring;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskExecutionDAO;
import io.vertigo.orchestra.dao.execution.OTaskLogDAO;
import io.vertigo.orchestra.dao.execution.OTaskWorkspaceDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.execution.OTaskLog;
import io.vertigo.orchestra.domain.execution.OTaskWorkspace;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.monitoring.MonitoringServices;

/**
 * Implémentation des services de monitoring de la tour de contrôle.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class MonitoringServicesImpl implements MonitoringServices {

	@Inject
	private OProcessDAO processDAO;
	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OTaskExecutionDAO taskExecutionDAO;

	@Inject
	private OTaskWorkspaceDAO taskWorkspaceDAO;
	@Inject
	private OTaskLogDAO taskLogDAO;
	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;

	/** {@inheritDoc} */
	@Override
	public DtList<OProcess> getProcesses() {
		return processDAO.getProcesses();
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getPlanificationsByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processPlanificationDAO.getPlanificationsByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessExecution> getExecutionsByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processExecutionDAO.getExecutionsByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OTaskExecution> getTaskExecutionsByPreId(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return taskExecutionDAO.getTaskExecutionsByPreId(preId);
	}

	/** {@inheritDoc} */
	@Override
	public OTaskWorkspace getTaskWorkspaceByTkeId(final Long tkeId, final boolean isIn) {
		Assertion.checkNotNull(tkeId);
		// ---
		return taskWorkspaceDAO.getTaskWorkspace(tkeId, isIn);
	}

	/** {@inheritDoc} */
	@Override
	public Option<OTaskLog> getTaskLogByTkeId(final Long tkeId) {
		Assertion.checkNotNull(tkeId);
		// ---
		return taskLogDAO.getTaskLogByTkeId(tkeId);

	}

}
