package io.vertigo.orchestra.impl.planner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

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

	@Inject
	@Named("nodeName")
	private String nodeName;
	@Inject
	@Named("planningPeriod")
	private Integer planningPeriod;
	@Inject
	@Named("forecastDuration")
	private Integer forecastDuration;

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
		Assertion.checkNotNull(planningPeriod);
		Assertion.checkNotNull(forecastDuration);
		Assertion.checkArgNotEmpty(nodeName);
		// ---
		processScheduler = new ProcessScheduler(processPlannerManager, planningPeriod);
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
		for (final OProcess process : processDefinitionManager.getRecurrentProcesses()) {
			final Option<Date> nextPlanification = findNextPlanificationTime(process);
			if (nextPlanification.isDefined()) {
				plannProcessAt(process.getProId(), nextPlanification.get());
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessPlanification> getProcessToExecute() {
		final GregorianCalendar lowerLimit = new GregorianCalendar(Locale.FRANCE);
		lowerLimit.add(Calendar.SECOND, -(planningPeriod / 2 + 1));

		final GregorianCalendar upperLimit = new GregorianCalendar(Locale.FRANCE);
		upperLimit.add(Calendar.SECOND, (planningPeriod / 2));

		planificationPAO.reserveProcessToExecute(lowerLimit.getTime(), upperLimit.getTime(), nodeName);
		return processPlanificationDAO.getProcessToExecute(nodeName);
	}

	/** {@inheritDoc} */
	@Override
	public void triggerPlanification(final OProcessPlanification processPlanification) {
		processPlanification.setPstCd(PlanificationState.TRIGGERED.name());
		processPlanificationDAO.save(processPlanification);

	}

	/** {@inheritDoc} */
	@Override
	public void misfirePlanification(final OProcessPlanification processPlanification) {
		processPlanification.setPstCd(PlanificationState.MISFIRED.name());
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

	/**
	 * TODO : Description de la méthode.
	 * @param process.
	 * @throws ParseException
	 */
	private Option<Date> findNextPlanificationTime(final OProcess process) {
		final Option<OProcessPlanification> lastPlanificationOption = getLastPlanificationsByProcess(process.getProId());

		try {
			final CronExpression cronExpression = new CronExpression(process.getCronExpression());

			if (!lastPlanificationOption.isDefined()) {
				return Option.<Date> some(new Date(cronExpression.getTimeAfter(new Date()).getTime() + planningPeriod / 2 * 1000)); // Normalement ca doit être bon quelque soit la synchronisation entre les deux timers (même fréquence)
			} else {
				final OProcessPlanification lastPlanification = lastPlanificationOption.get();
				final Date nextPotentialPlainification = cronExpression.getTimeAfter(lastPlanification.getExpectedTime());
				if (nextPotentialPlainification.before(new Date(System.currentTimeMillis() + forecastDuration * 1000))) {
					return Option.<Date> some(nextPotentialPlainification);
				}
			}
		} catch (final ParseException e) {
			throw new RuntimeException("Process' cron expression is not valid, process cannot be planned");
		}

		return Option.<Date> none();

	}

}
