package io.vertigo.orchestra.impl.process.schedule;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.process.execution.ProcessExecutor;
import io.vertigo.orchestra.process.schedule.ProcessScheduler;

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
