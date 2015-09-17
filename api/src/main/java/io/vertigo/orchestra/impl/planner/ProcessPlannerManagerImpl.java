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
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.planner.PlanificationState;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessPlannerManagerImpl implements ProcessPlannerManager {
	private final long timerDelay = 10 * 1000;
	private final long forecastDuration = 10 * 1000 * 30;
	private ProcessScheduler processScheduler;

	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;

	//--------------------------------------------------------------------------------------------------
	//--- Initialisation
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void postStart(final ProcessPlannerManager processPlannerManager) {
		processScheduler = new ProcessScheduler(processPlannerManager, timerDelay);
		processScheduler.start();

	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void plannProcessAt(final Long proId, final Date planifiedTime) {

		final OProcessPlanification processPlanification = new OProcessPlanification();
		processPlanification.setProId(proId);
		processPlanification.setExpectedTime(planifiedTime);
		processPlanification.setPstCd(PlanificationState.WAITING.name());
		processPlanificationDAO.save(processPlanification);

	}

	/** {@inheritDoc} */
	@Override
	public void plannRecurrentProcesses() {
		for (final OProcess process : processDefinitionManager.getActiveProcesses()) {
			if ("RECURRENT".equals(process.getTrtCd())) {
				final Option<OProcessPlanification> lastPlanificationOption = getLastPlanificationsByProcess(process.getProId());
				if (!lastPlanificationOption.isDefined()) {
					plannProcessAt(process.getProId(),
							new Date(System.currentTimeMillis() + timerDelay));
				} else {
					final OProcessPlanification lastPlanification = lastPlanificationOption.get();
					if (lastPlanification.getExpectedTime().getTime()
							+ process.getDelay() < System.currentTimeMillis() + forecastDuration) {
						plannProcessAt(process.getProId(),
								new Date(lastPlanification.getExpectedTime().getTime() + process.getDelay()));
					}
				}
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getProcessToExecute() {
		planificationPAO.reserveProcessToExecute();
		return processPlanificationDAO.getProcessToExecute();
	}

	/** {@inheritDoc} */
	@Override
	public void triggerPlanification(final OProcessPlanification processPlanification) {
		processPlanification.setPstCd(PlanificationState.TRIGGERED.name());
		processPlanificationDAO.save(processPlanification);

	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private Option<OProcessPlanification> getLastPlanificationsByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processPlanificationDAO.getLastPlanificationByProId(proId);
	}

}
