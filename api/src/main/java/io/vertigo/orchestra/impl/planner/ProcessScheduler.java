package io.vertigo.orchestra.impl.planner;

import java.util.Timer;
import java.util.TimerTask;

import io.vertigo.lang.Activeable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
final class ProcessScheduler implements Activeable {
	private Timer planTimer;
	private final long timerDelay;

	private final ProcessPlannerManager processPlannerManager;

	ProcessScheduler(final ProcessPlannerManager processPlannerManager, final long timerDelay) {
		Assertion.checkNotNull(processPlannerManager);
		Assertion.checkNotNull(timerDelay);
		//-----
		this.processPlannerManager = processPlannerManager;
		this.timerDelay = timerDelay;
	}

	@Override
	public void start() {
		planTimer = new Timer("PlannNewProcesses", true);
		planTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				processPlannerManager.plannRecurrentProcesses();

			}
		}, timerDelay, timerDelay);
	}

	@Override
	public void stop() {
		// shutdown >>>>
	}

}
