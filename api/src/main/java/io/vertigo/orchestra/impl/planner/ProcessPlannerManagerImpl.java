package io.vertigo.orchestra.impl.planner;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessPlannerManagerImpl implements ProcessPlannerManager {
	private ProcessScheduler processScheduler;

	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getProcessToExecute() {
		planificationPAO.reserveProcessToExecute();
		return processPlanificationDAO.getProcessToExecute();
	}

	/** {@inheritDoc} */
	@Override
	public void plannProcessAt(final Long proId, final Date planifiedTime) {

		final OProcessPlanification processPlanification = new OProcessPlanification();
		processPlanification.setProId(proId);
		processPlanification.setExpectedTime(planifiedTime);
		processPlanification.setPstCd("WAITING"); // TODO : A refaire
		processPlanificationDAO.save(processPlanification);

	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getProcessesPlanned(final Date from, final Date to) {
		// TODO
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getPlanificationsByProcess(final Long proId) {
		// TODO
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Option<OProcessPlanification> getLastPlanificationsByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processPlanificationDAO.getLastPlanificationByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public void triggerPlanification(final OProcessPlanification processPlanification) {
		processPlanification.setPstCd("TRIGGERED");
		processPlanificationDAO.save(processPlanification);

	}

	/** {@inheritDoc} */
	@Override
	public void postStart(final ProcessPlannerManager processPlannerManager) {
		processScheduler = new ProcessScheduler(processPlannerManager, processDefinitionManager);
		processScheduler.start();

	}
}
