package io.vertigo.orchestra.impl.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
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
import io.vertigo.lang.Plugin;
import io.vertigo.lang.WrappedException;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.execution.NodeManager;
import io.vertigo.orchestra.scheduler.PlanificationState;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerPlugin implements Plugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(ProcessSchedulerPlugin.class);

	private final long timerDelay;

	private final Long nodId;
	private final ScheduledExecutorService localScheduledExecutor;
	private final int planningPeriodSeconds;
	private final int forecastDurationSeconds;

	@Inject
	private VTransactionManager transactionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;
	@Inject
	private OProcessDAO processDao;

	/**
	 * Constructeur.
	 * @param nodeManager le gestionnaire de noeud
	 * @param nodeName le nom du noeud
	 * @param planningPeriodSeconds le timer de planfication
	 * @param forecastDurationSeconds la durée de prévision des planifications
	 */
	@Inject
	public ProcessSchedulerPlugin(
			final NodeManager nodeManager,
			@Named("nodeName") final String nodeName,
			@Named("planningPeriodSeconds") final int planningPeriodSeconds,
			@Named("forecastDurationSeconds") final int forecastDurationSeconds) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(planningPeriodSeconds);
		Assertion.checkNotNull(forecastDurationSeconds);
		//-----
		timerDelay = planningPeriodSeconds * 1000L;
		// We register the node
		nodId = nodeManager.registerNode(nodeName);
		// ---
		Assertion.checkNotNull(nodId);
		// ---
		this.planningPeriodSeconds = planningPeriodSeconds;
		this.forecastDurationSeconds = forecastDurationSeconds;
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
		}, timerDelay / 2, timerDelay, TimeUnit.MILLISECONDS);
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

	void scheduleAt(final Long proId, final Date planifiedTime, final Optional<String> initialParamsOption) {
		Assertion.checkNotNull(proId);
		// ---
		final OProcessPlanification processPlanification = new OProcessPlanification();
		processPlanification.setProId(proId);
		processPlanification.setExpectedTime(planifiedTime);
		processPlanification.setPstCd(PlanificationState.WAITING.name());
		if (initialParamsOption.isPresent()) {
			processPlanification.setInitialParams(initialParamsOption.get());
		}
		processPlanificationDAO.save(processPlanification);

	}

	void plannRecurrentProcesses() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			for (final OProcess process : getAllScheduledProcesses()) {
				final Optional<Date> nextPlanification = findNextPlanificationTime(process);
				if (nextPlanification.isPresent()) {
					scheduleAt(process.getProId(), nextPlanification.get(), Optional.ofNullable(process.getInitialParams()));
				}
			}
			transaction.commit();
		}

	}

	List<Long> getProcessToExecute() {
		final GregorianCalendar lowerLimit = new GregorianCalendar(Locale.FRANCE);
		lowerLimit.add(Calendar.SECOND, -planningPeriodSeconds * 5 / 4); //Just to be sure that nothing will be lost

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

	void resetFuturePlanificationOfProcess(final String processName) {
		planificationPAO.cleanFuturePlanifications(processName);
	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private Optional<OProcessPlanification> getLastPlanificationsByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processPlanificationDAO.getLastPlanificationByProId(proId);
	}

	private Optional<Date> findNextPlanificationTime(final OProcess process) {
		final Optional<OProcessPlanification> lastPlanificationOption = getLastPlanificationsByProcess(process.getProId());

		try {
			final CronExpression cronExpression = new CronExpression(process.getCronExpression());

			if (!lastPlanificationOption.isPresent()) {
				final Date now = new Date();
				final Date compatibleNow = new Date(now.getTime() + (planningPeriodSeconds / 2 * 1000L));// Normalement ca doit être bon quelque soit la synchronisation entre les deux timers (même fréquence)
				return Optional.of(cronExpression.getNextValidTimeAfter(compatibleNow));
			}
			final OProcessPlanification lastPlanification = lastPlanificationOption.get();
			final Date nextPotentialPlainification = cronExpression.getNextValidTimeAfter(lastPlanification.getExpectedTime());
			if (nextPotentialPlainification.before(new Date(System.currentTimeMillis() + forecastDurationSeconds * 1000L))) {
				return Optional.of(nextPotentialPlainification);
			}
		} catch (final ParseException e) {
			throw new WrappedException("Process' cron expression is not valid, process cannot be planned", e);
		}

		return Optional.<Date> empty();

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
			final long ageOfPlanification = (now.getTime() - planification.getExpectedTime().getTime()) / (60 * 1000L);// in seconds
			if (ageOfPlanification < process.getRescuePeriod()) {
				planification.setPstCd(PlanificationState.RESCUED.name());
			} else {
				planification.setPstCd(PlanificationState.MISFIRED.name());
			}
			processPlanificationDAO.save(planification);
		}

	}

}
