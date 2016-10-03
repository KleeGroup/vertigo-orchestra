package io.vertigo.orchestra.impl.scheduler.plugins;

import java.util.Date;
import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessSchedulerPlugin extends Plugin {

	/**
	 * @see io.vertigo.orchestra.scheduler.ProcessSchedulerManager#scheduleWithCron(ProcessDefinition)
	 */
	void scheduleWithCron(final ProcessDefinition processDefinition);

	/**
	 * @see io.vertigo.orchestra.scheduler.ProcessSchedulerManager#scheduleAt(ProcessDefinition, Date, Optional)
	 */
	void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParamsOption);

	/**
	 * Retourne le type de processus géré par le plugin
	 * @return le type de processus géré
	 */
	ProcessType getHandledProcessType();

}
