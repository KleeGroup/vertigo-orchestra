package io.vertigo.orchestra.impl.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.NodeManager;
import io.vertigo.orchestra.scheduler.PlanificationState;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerPlugin implements Plugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(ProcessSchedulerPlugin.class);

	private final long timerDelay;

	private final Long nodId;
	private final ScheduledExecutorService localScheduledExecutor;
	private final Integer planningPeriod;
	private final Integer forecastDuration;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;
	@Inject
	private OProcessDAO processDao;

	@Inject
	public ProcessSchedulerPlugin(final NodeManager nodeManager, @Named("nodeName") final String nodeName, @Named("planningPeriod") final Integer planningPeriod, @Named("forecastDuration") final Integer forecastDuration) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(planningPeriod);
		Assertion.checkNotNull(forecastDuration);
		//-----
		timerDelay = planningPeriod * 1000;
		// We register the node
		nodId = nodeManager.registerNode(nodeName);
		// ---
		Assertion.checkNotNull(nodId);
		// ---
		this.planningPeriod = planningPeriod;
		this.forecastDuration = forecastDuration;
		localScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void start() {

		localScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					plannRecurrentProcesses();
				} catch (final Exception e) {
					// We log the error and we continue the timer
					LOGGER.error("Exception planning recurrent processes", e);
				}

			}
		}, 0, timerDelay, TimeUnit.MILLISECONDS);
		// We clean the planification
		cleanPastPlanification();
	}

	@Override
	public void stop() {
		localScheduledExecutor.shutdown();
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
			for (final OProcess process : getAllScheduledProcesses()) {
				final Option<Date> nextPlanification = findNextPlanificationTime(process);
				if (nextPlanification.isDefined()) {
					scheduleAt(process.getProId(), nextPlanification.get(), Option.option(process.getInitialParams()));
				}
			}
			transaction.commit();
		}

	}

	List<Long> getProcessToExecute() {
		final GregorianCalendar lowerLimit = new GregorianCalendar(Locale.FRANCE);
		lowerLimit.add(Calendar.SECOND, -planningPeriod * 5 / 4); //Just to be sure that nothing will be lost

		final GregorianCalendar upperLimit = new GregorianCalendar(Locale.FRANCE);

		planificationPAO.reserveProcessToExecute(lowerLimit.getTime(), upperLimit.getTime(), nodId);
		final DtList<OProcessPlanification> processToExecute = processPlanificationDAO.getProcessToExecute(nodId);
		final List<Long> prpIdsToExecute = new ArrayList<>();
		for (final OProcessPlanification processPlanification : processToExecute) {
			prpIdsToExecute.add(processPlanification.getPrpId());
		}
		return prpIdsToExecute;
	}

	void triggerPlanification(final Long prpId) {
		final OProcessPlanification processPlanification = processPlanificationDAO.get(prpId);
		processPlanification.setPstCd(PlanificationState.TRIGGERED.name());
		processPlanificationDAO.save(processPlanification);

	}

	void misfirePlanification(final Long prpId) {
		final OProcessPlanification processPlanification = processPlanificationDAO.get(prpId);
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
				final Date now = new Date();
				final Date compatibleNow = new Date(now.getTime() + (planningPeriod / 2 * 1000));// Normalement ca doit être bon quelque soit la synchronisation entre les deux timers (même fréquence)
				return Option.<Date> some(cronExpression.getNextValidTimeAfter(compatibleNow));
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

	private DtList<OProcess> getAllScheduledProcesses() {
		return processDao.getAllScheduledProcesses();
	}

	// clean Planification on startup

	private void cleanPastPlanification() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			doCleanPastPlanification();
			transaction.commit();
		}
	}

	private void doCleanPastPlanification() {
		final Date now = new Date();
		planificationPAO.cleanPlanificationsOnBoot(now);
		// ---
		for (final OProcessPlanification planification : processPlanificationDAO.getAllLastPastPlanifications(now)) {
			// We check the process policy of validity
			final OProcess process = planification.getProcessus();
			final Long ageOfPlanification = (now.getTime() - planification.getExpectedTime().getTime()) / (60 * 1000);// in seconds
			if (ageOfPlanification < process.getRescuePeriod()) {
				planification.setPstCd(PlanificationState.RESCUED.name());
			} else {
				planification.setPstCd(PlanificationState.MISFIRED.name());
			}
			processPlanificationDAO.save(planification);
		}

	}

}