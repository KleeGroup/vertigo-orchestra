package io.vertigo.orchestra.scheduler;

import java.util.Date;
import java.util.Map;
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
	 * @param proId l'id du processus à planifier
	 * @param planifiedTime la date de planification
	 * @param initialParams les paramètres initiaux à utiliser
	 */
	void scheduleAt(final Long proId, final Date planifiedTime, final Optional<String> initialParams);

	/**
	 * Retourne la liste des executions qui doivent etre lancées maintenant avec des eventuels paramètres initiaux supplémentaires.
	 * @return la liste de processus
	 */
	Map<ProcessDefinition, String> getProcessToExecute();

}
