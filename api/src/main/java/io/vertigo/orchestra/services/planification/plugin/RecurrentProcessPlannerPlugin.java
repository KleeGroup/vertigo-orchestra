package io.vertigo.orchestra.services.planification.plugin;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Option;
import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.services.definition.ProcessServices;
import io.vertigo.orchestra.services.planification.ProcessPlanificationServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class RecurrentProcessPlannerPlugin implements Plugin, Activeable {

	private Timer planTimer;
	private final long timerDelay = 10 * 1000;
	private final long forecastDuration = 10 * 1000 * 30;

	@Inject
	private ProcessPlanificationServices processPlanificationServices;

	@Inject
	private ProcessServices processServices;

	/** {@inheritDoc} */
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

	/** {@inheritDoc} */
	@Override
	public void stop() {
		// TODO

	}

	/** {@inheritDoc} */
	private void plannRecurrentProcesses() {
		for (final OProcess process : processServices.getActiveProcesses()) {
			if ("RECURRENT".equals(process.getTrtCd())) {
				final Option<OProcessPlanification> lastPlanificationOption = processPlanificationServices
						.getLastPlanificationsByProcess(process.getProId());
				if (!lastPlanificationOption.isDefined()) {
					processPlanificationServices.plannProcessAt(process.getProId(),
							new Date(System.currentTimeMillis() + timerDelay));
				} else {
					final OProcessPlanification lastPlanification = lastPlanificationOption.get();
					if ((lastPlanification.getExpectedTime().getTime()
							+ process.getDelay()) < (System.currentTimeMillis() + forecastDuration)) {
						processPlanificationServices.plannProcessAt(process.getProId(),
								new Date(lastPlanification.getExpectedTime().getTime() + process.getDelay()));
					}
				}
			}
		}
	}

}
