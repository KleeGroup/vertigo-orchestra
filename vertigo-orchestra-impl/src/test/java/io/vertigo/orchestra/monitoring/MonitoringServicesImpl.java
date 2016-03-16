package io.vertigo.orchestra.monitoring;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.OActivityExecutionDAO;
import io.vertigo.orchestra.dao.execution.OActivityLogDAO;
import io.vertigo.orchestra.dao.execution.OActivityWorkspaceDAO;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.domain.execution.OActivityWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * Implémentation des services de monitoring de la tour de contrôle.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class MonitoringServicesImpl implements MonitoringServices {

	@Inject
	private OProcessExecutionDAO processExecutionDAO;
	@Inject
	private OActivityExecutionDAO activityExecutionDAO;

	@Inject
	private OActivityWorkspaceDAO activityWorkspaceDAO;
	@Inject
	private OActivityLogDAO activityLogDAO;
	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;

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
	public DtList<OActivityExecution> getActivityExecutionsByPreId(final Long preId) {
		Assertion.checkNotNull(preId);
		// ---
		return activityExecutionDAO.getActivityExecutionsByPreId(preId);
	}

	/** {@inheritDoc} */
	@Override
	public OActivityWorkspace getActivityWorkspaceByAceId(final Long aceId, final boolean isIn) {
		Assertion.checkNotNull(aceId);
		// ---
		return activityWorkspaceDAO.getActivityWorkspace(aceId, isIn);
	}

	/** {@inheritDoc} */
	@Override
	public Option<OActivityLog> getActivityLogByAceId(final Long aceId) {
		Assertion.checkNotNull(aceId);
		// ---
		return activityLogDAO.getActivityLogByAceId(aceId);

	}

}
