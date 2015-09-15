package io.vertigo.orchestra.impl.planner;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
final class ProcessScheduler implements Activeable {
	private Timer planTimer;
	private final long timerDelay = 10 * 1000;
	private final long forecastDuration = 10 * 1000 * 30;

	private final ProcessPlannerManager processPlannerManager;

	private final ProcessDefinitionManager processDefinitionManager;

	ProcessScheduler(final ProcessPlannerManager processPlannerManager, final ProcessDefinitionManager processDefinitionManager) {
		Assertion.checkNotNull(processPlannerManager);
		Assertion.checkNotNull(processDefinitionManager);
		//-----
		this.processDefinitionManager = processDefinitionManager;
		this.processPlannerManager = processPlannerManager;
	}

	@Override
	public void start() {
		planTimer = new Timer("PlannNewProcesses", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				plannRecurrentProcesses();

			}
		}, timerDelay, timerDelay);
	}

	private void plannRecurrentProcesses() {
		for (final OProcess process : processDefinitionManager.getActiveProcesses()) {
			if ("RECURRENT".equals(process.getTrtCd())) {
				final Option<OProcessPlanification> lastPlanificationOption = processPlannerManager
						.getLastPlanificationsByProcess(process.getProId());
				if (!lastPlanificationOption.isDefined()) {
					processPlannerManager.plannProcessAt(process.getProId(),
							new Date(System.currentTimeMillis() + timerDelay));
				} else {
					final OProcessPlanification lastPlanification = lastPlanificationOption.get();
					if (lastPlanification.getExpectedTime().getTime()
							+ process.getDelay() < System.currentTimeMillis() + forecastDuration) {
						processPlannerManager.plannProcessAt(process.getProId(),
								new Date(lastPlanification.getExpectedTime().getTime() + process.getDelay()));
					}
				}
			}
		}
	}

	@Override
	public void stop() {
		// shutdown >>>>
	}

}
