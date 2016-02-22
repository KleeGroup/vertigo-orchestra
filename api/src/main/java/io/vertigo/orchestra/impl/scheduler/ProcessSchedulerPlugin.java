package io.vertigo.orchestra.impl.scheduler;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.scheduler.PlanificationState;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerPlugin implements Plugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(ProcessSchedulerPlugin.class);

	private Timer planTimer;
	private final long timerDelay;

	private final String nodeName;
	private final Integer planningPeriod;
	private final Integer forecastDuration;

	@Inject
	private ProcessDefinitionManager processDefinitionManager;
	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;

	@Inject
	public ProcessSchedulerPlugin(@Named("nodeName") final String nodeName, @Named("planningPeriod") final Integer planningPeriod, @Named("forecastDuration") final Integer forecastDuration) {
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(planningPeriod);
		Assertion.checkNotNull(forecastDuration);
		//-----
		timerDelay = planningPeriod * 1000;
		this.nodeName = nodeName;
		this.planningPeriod = planningPeriod;
		this.forecastDuration = forecastDuration;
	}

	@Override
	public void start() {
		planTimer = new Timer("PlannNewProcesses", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					plannRecurrentProcesses();
				} catch (final Exception e) {
					// We log the error and we continue the timer
					LOGGER.error("Exception planning recurrent processes", e);
				}

			}
		}, timerDelay, timerDelay);
	}

	@Override
	public void stop() {
		planTimer.cancel();
		planTimer.purge();
	}

	//--------------------------------------------------------------------------------------------------
	//--- Package
	//--------------------------------------------------------------------------------------------------

	void scheduleAt(final Long proId, final Date planifiedTime, final Option<String> initialParamsOption) {
		Assertion.checkNotNull(proId);
		// ---
		final OProcessPlanification processPlanification = new OProcessPlanification();
		processPlanification.setProId(proId);
		processPlanification.setExpectedTime(planifiedTime);
		processPlanification.setPstCd(PlanificationState.WAITING.name());
		if (initialParamsOption.isDefined()) {
			processPlanification.setInitialParams(initialParamsOption.get());
		}
		processPlanificationDAO.save(processPlanification);

	}

	private void plannRecurrentProcesses() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			for (final OProcess process : processDefinitionManager.getRecurrentProcesses()) {
				final Option<Date> nextPlanification = findNextPlanificationTime(process);
				if (nextPlanification.isDefined()) {
					scheduleAt(process.getProId(), nextPlanification.get(), Option.option(process.getInitialParams()));
				}
			}
			transaction.commit();
		}

	}

	DtList<OProcessPlanification> getProcessToExecute() {
		final GregorianCalendar lowerLimit = new GregorianCalendar(Locale.FRANCE);
		lowerLimit.add(Calendar.SECOND, -(planningPeriod / 2 + 1));

		final GregorianCalendar upperLimit = new GregorianCalendar(Locale.FRANCE);
		upperLimit.add(Calendar.SECOND, (planningPeriod / 2));

		planificationPAO.reserveProcessToExecute(lowerLimit.getTime(), upperLimit.getTime(), nodeName);
		return processPlanificationDAO.getProcessToExecute(nodeName);
	}

	void triggerPlanification(final OProcessPlanification processPlanification) {
		processPlanification.setPstCd(PlanificationState.TRIGGERED.name());
		processPlanificationDAO.save(processPlanification);

	}

	void misfirePlanification(final OProcessPlanification processPlanification) {
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
	 */
	private Option<Date> findNextPlanificationTime(final OProcess process) {
		final Option<OProcessPlanification> lastPlanificationOption = getLastPlanificationsByProcess(process.getProId());

		try {
			final CronExpression cronExpression = new CronExpression(process.getCronExpression());

			if (!lastPlanificationOption.isDefined()) {
				return Option.<Date> some(new Date(cronExpression.getNextValidTimeAfter(new Date()).getTime() + planningPeriod / 2 * 1000)); // Normalement ca doit être bon quelque soit la synchronisation entre les deux timers (même fréquence)
			}
			final OProcessPlanification lastPlanification = lastPlanificationOption.get();
			final Date nextPotentialPlainification = cronExpression.getNextValidTimeAfter(lastPlanification.getExpectedTime());
			if (nextPotentialPlainification.before(new Date(System.currentTimeMillis() + forecastDuration * 1000))) {
				return Option.<Date> some(nextPotentialPlainification);
			}
		} catch (final ParseException e) {
			throw new RuntimeException("Process' cron expression is not valid, process cannot be planned");
		}

		return Option.<Date> none();

	}

}
