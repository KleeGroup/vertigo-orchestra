package io.vertigo.orchestra.process.schedule;

import java.util.Date;
import java.util.Map;

import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessScheduler {

	/**
	 * Planifie les executions d'un job en fonction de l'expression cron associée.
	 * @param processDefinition le processus à planifier de manière recurrente grace à son expression Cron
	 */
	void scheduleWithCron(final ProcessDefinition processDefinition);

	/**
	 * Planifie un processus à une date donnée.
	 * @param processDefinition le processus à planifier
	 * @param planifiedTime la date de planification
	 * @param initialParams les paramètres initiaux à utiliser
	 */
	void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Map<String, String> initialParams);

}
