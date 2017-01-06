package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.vertigo.lang.Manager;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * Orchestra high-level services.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface OrchestraManager extends Manager {

	/**
	 * Creer ou mettre à jour un processus orchestra.
	 * @param processDefinition la définition à créer ou mettre à jour.
	 */
	void createOrUpdateDefinition(ProcessDefinition processDefinition);

	/**
	 * Récupère l'ensemble des processus gérés par orchestra.
	 * @return la liste des processus
	 */
	List<ProcessDefinition> getAllProcessDefinitions();

	/**
	 * Programme à une date donnée une execution d'un processus donné.
	 * @param processName Le nom du processus à lancer
	 * @param expectedTime La date à laquelle il doit être lancé
	 * @param initialParams Les paramètres initiaux à utiliser
	 */
	void scheduleAt(String processName, Date expectedTime, Map<String, String> initialParams);

	/**
	 * Programme de manière récurrent des execution en se basant sur l'expression Cron du processus.
	 * @param processName Le nom du processus à programmer
	 */
	void scheduleWithCron(String processName);
}
