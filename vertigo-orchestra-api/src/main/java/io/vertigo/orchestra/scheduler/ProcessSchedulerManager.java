package io.vertigo.orchestra.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Manager;

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
	 * Retourne la liste des executions qui doivent etre lancées maintenant.
	 * @return la liste de processus
	 */
	List<Long> getProcessToExecute();

	/**
	 * Passe une planification à triggered.
	 * @param prpId l'id de la planification
	 */
	void triggerPlanification(final Long prpId);

	/**
	 * Passe une planification à misfired.
	 * @param prpId l'id de la planification
	 */
	void misfirePlanification(final Long prpId);

	/**
	 * Remet à zéro les planification futures d'un processus.
	 * @param processName le nom du processus à impacter
	 */
	void resetFuturePlanificationOfProcess(final String processName);

}
