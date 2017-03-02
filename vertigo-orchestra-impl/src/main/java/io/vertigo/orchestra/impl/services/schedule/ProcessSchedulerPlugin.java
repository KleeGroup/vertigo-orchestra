package io.vertigo.orchestra.impl.services.schedule;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definitions.ProcessType;
import io.vertigo.orchestra.services.execution.ProcessExecutor;
import io.vertigo.orchestra.services.schedule.ProcessScheduler;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessSchedulerPlugin extends ProcessScheduler, Plugin {
	void start(ProcessExecutor processExecutor);

	/**
	 * Retourne le type de processus géré par le plugin
	 * @return le type de processus géré
	 */
	ProcessType getHandledProcessType();
}
