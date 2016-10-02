package io.vertigo.orchestra.scheduler;

import java.util.Date;
import java.util.Optional;

import io.vertigo.lang.Manager;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessSchedulerManager extends Manager {

	/**
	 * Planifie un processus à une date donnée.
	 * @param processDefinition le processus à planifier
	 * @param planifiedTime la date de planification
	 * @param initialParams les paramètres initiaux à utiliser
	 */
	void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParams);

}
