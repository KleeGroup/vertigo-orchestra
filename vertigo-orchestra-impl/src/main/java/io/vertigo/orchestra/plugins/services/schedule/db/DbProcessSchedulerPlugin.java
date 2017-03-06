package io.vertigo.orchestra.plugins.services.schedule.db;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import io.vertigo.commons.daemon.DaemonManager;
import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.WrappedException;
import io.vertigo.orchestra.dao.execution.OProcessExecutionDAO;
import io.vertigo.orchestra.dao.planification.OProcessPlanificationDAO;
import io.vertigo.orchestra.dao.planification.PlanificationPAO;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessType;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.impl.node.NodeManager;
import io.vertigo.orchestra.impl.services.schedule.CronExpression;
import io.vertigo.orchestra.impl.services.schedule.ProcessSchedulerPlugin;
import io.vertigo.orchestra.plugins.services.MapCodec;
import io.vertigo.orchestra.services.execution.ProcessExecutor;
import io.vertigo.orchestra.services.schedule.SchedulerState;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DbProcessSchedulerPlugin implements ProcessSchedulerPlugin, Activeable {

	private static final Logger LOGGER = Logger.getLogger(DbProcessSchedulerPlugin.class);

	//private final long timerDelay;

	private final Long nodId;
	private final int planningPeriodSeconds;
	private final int forecastDurationSeconds;

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private DaemonManager daemonManager;

	@Inject
	private OrchestraDefinitionManager definitionManager;

	@Inject
	private OProcessPlanificationDAO processPlanificationDAO;
	@Inject
	private PlanificationPAO planificationPAO;
	@Inject
	private OProcessExecutionDAO processExecutionDAO;

	private final MapCodec mapCodec = new MapCodec();

	/**
	 * Constructeur.
	 * @param nodeManager le gestionnaire de noeud
	 * @param nodeName le nom du noeud
	 * @param planningPeriodSeconds le timer de planfication
	 * @param forecastDurationSeconds la durée de prévision des planifications
	 */
	@Inject
	public DbProcessSchedulerPlugin(
			final NodeManager nodeManager,
			@Named("nodeName") final String nodeName,
			@Named("planningPeriodSeconds") final int planningPeriodSeconds,
			@Named("forecastDurationSeconds") final int forecastDurationSeconds) {
		Assertion.checkNotNull(nodeManager);
		Assertion.checkNotNull(nodeName);
		Assertion.checkNotNull(planningPeriodSeconds);
		Assertion.checkNotNull(forecastDurationSeconds);
		//-----
		//timerDelay = planningPeriodSeconds * 1000L;
		// We register the node
		nodId = nodeManager.registerNode(nodeName);
		// ---
		Assertion.checkNotNull(nodId);
		// ---
		this.planningPeriodSeconds = planningPeriodSeconds;
		this.forecastDurationSeconds = forecastDurationSeconds;
		//localScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
	}

	@Override
	public void start() {
		//
	}

	@Override
	public void start(final ProcessExecutor processExecutor) {
		Assertion.checkNotNull(processExecutor);
		//---
		daemonManager.registerDaemon("O_DB_PROCESS_SCHEDULER_DAEMON", () -> () -> {
			try {
				plannRecurrentProcesses();
				initToDo(processExecutor);
			} catch (final Exception e) {
				// We log the error and we continue the timer
				LOGGER.error("Exception planning recurrent processes", e);
			}

		}, planningPeriodSeconds);

		// We clean the planification
		cleanPastPlanification();

	}

	@Override
	public void stop() {
		// rien
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.SUPERVISED;
	}
	//--------------------------------------------------------------------------------------------------
	//--- Package
	//--------------------------------------------------------------------------------------------------

	@Override
	public void scheduleWithCron(final ProcessDefinition processDefinition) {
		if (transactionManager.hasCurrentTransaction()) {
			doScheduleWithCron(processDefinition);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doScheduleWithCron(processDefinition);
				transaction.commit();
			}
		}

	}

	private void doScheduleWithCron(final ProcessDefinition processDefinition) {
		final Optional<Date> nextPlanification = findNextPlanificationTime(processDefinition);
		if (nextPlanification.isPresent()) {
			scheduleAt(processDefinition, nextPlanification.get(), processDefinition.getTriggeringStrategy().getInitialParams());
		}
	}

	@Override
	public void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Map<String, String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(planifiedTime);
		Assertion.checkNotNull(initialParams);
		//---
		if (transactionManager.hasCurrentTransaction()) {
			doScheduleAt(processDefinition, planifiedTime, initialParams);
		} else {
			try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
				doScheduleAt(processDefinition, planifiedTime, initialParams);
				transaction.commit();
			}
		}
	}

	//--------------------------------------------------------------------------------------------------
	//--- Private
	//--------------------------------------------------------------------------------------------------

	private void doScheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Map<String, String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		// ---
		final OProcessPlanification processPlanification = new OProcessPlanification();
		processPlanification.setProId(processDefinition.getId());
		processPlanification.setExpectedTime(planifiedTime);
		changeState(processPlanification, SchedulerState.WAITING);
		processPlanification.setInitialParams(mapCodec.encode(initialParams));
		processPlanificationDAO.save(processPlanification);

	}

	private void initToDo(final ProcessExecutor processExecutor) {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			initNewProcessesToLaunch(processExecutor);
			transaction.commit();
		}
	}

	private void initNewProcessesToLaunch(final ProcessExecutor processExecutor) {
		for (final OProcessPlanification processPlanification : getPlanificationsToTrigger()) {
			final ProcessDefinition processDefinition = definitionManager.getProcessDefinition(processPlanification.getProcessus().getName());
			if (canExecute(processDefinition)) {
				triggerPlanification(processPlanification);
				processExecutor.execute(processDefinition, Optional.ofNullable(processPlanification.getInitialParams()));
			} else {
				misfirePlanification(processPlanification);
			}
		}
	}

	private void plannRecurrentProcesses() {
		try (final VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			for (final ProcessDefinition processDefinition : getAllScheduledProcesses()) {
				scheduleWithCron(processDefinition);
			}
			transaction.commit();
		}

	}

	private DtList<OProcessPlanification> getPlanificationsToTrigger() {
		final GregorianCalendar lowerLimit = new GregorianCalendar(Locale.FRANCE);
		lowerLimit.add(Calendar.SECOND, -planningPeriodSeconds * 5 / 4); //Just to be sure that nothing will be lost

		final GregorianCalendar upperLimit = new GregorianCalendar(Locale.FRANCE);

		planificationPAO.reserveProcessToExecute(lowerLimit.getTime(), upperLimit.getTime(), nodId);
		return processPlanificationDAO.getProcessToExecute(nodId);
	}

	private boolean canExecute(final ProcessDefinition processDefinition) {
		// We check if process allow multiExecutions
		if (!processDefinition.getTriggeringStrategy().isMultiExecution()) {
			return processExecutionDAO.getActiveProcessExecutionByProId(processDefinition.getId()).isEmpty();
		}
		return true;

	}

	private Optional<OProcessPlanification> getLastPlanificationsByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processPlanificationDAO.getLastPlanificationByProId(proId);
	}

	private Optional<Date> findNextPlanificationTime(final ProcessDefinition processDefinition) {
		final Optional<OProcessPlanification> lastPlanificationOption = getLastPlanificationsByProcess(processDefinition.getId());

		try {
			final CronExpression cronExpression = new CronExpression(processDefinition.getTriggeringStrategy().getCronExpression().get());

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
			throw WrappedException.wrap(e, "Process' cron expression is not valid, process cannot be planned");
		}

		return Optional.<Date> empty();

	}

	private List<ProcessDefinition> getAllScheduledProcesses() {
		return definitionManager.getAllProcessDefinitionsByType(getHandledProcessType()).stream()
				.filter(processDefinition -> processDefinition.isActive())// We only want actives
				.filter(processDefinition -> processDefinition.getTriggeringStrategy().getCronExpression().isPresent())// We only want the processes to schedule
				.collect(Collectors.toList());
	}

	private void changeState(final OProcessPlanification processPlanification, final SchedulerState planificationState) {
		Assertion.checkNotNull(processPlanification);
		Assertion.checkNotNull(planificationState);
		// ---
		processPlanification.setSstCd(planificationState.name());
	}

	private void triggerPlanification(final OProcessPlanification processPlanification) {
		changeState(processPlanification, SchedulerState.TRIGGERED);
		processPlanificationDAO.save(processPlanification);
	}

	private void misfirePlanification(final OProcessPlanification processPlanification) {
		changeState(processPlanification, SchedulerState.MISFIRED);
		processPlanificationDAO.save(processPlanification);
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
				changeState(planification, SchedulerState.RESCUED);
			} else {
				changeState(planification, SchedulerState.MISFIRED);
			}
			processPlanificationDAO.save(planification);
		}

	}

}
