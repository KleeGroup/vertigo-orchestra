package io.vertigo.orchestra.definition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Manager;

/**
 * Interface (privé) de la gestion des définitions de processus.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Manager {
	//-----
	//-READ
	//-----
	/**
	 * Récupère une définition de processus par son nom.
	 * @param processName le nom du processus à récupérer
	 * @return la définition du processus
	 */
	ProcessDefinition getProcessDefinition(String processName);

	/**
	 * Récupère l'ensemble des processus gérés par orchestra.
	 * @return la liste des processus
	 */
	List<ProcessDefinition> getAllProcessDefinitions();

	/**
	 * Récupère l'ensemble des processus gérés par orchestra d'un type donné.
	 * @param processType le type de processus recherché
	 * @return la liste des processus
	 */
	List<ProcessDefinition> getAllProcessDefinitionsByType(ProcessType processType);

	//-----
	//-WRITE
	//-----
	/**
	 * Creer ou mettre à jour un processus orchestra.
	 * @param processDefinition la définition à créer ou mettre à jour.
	 */
	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

	/**
	 * Met à jour les propriétés d'une définition sans la rendre obsolète.
	 * @param processName le nom du processus à mettre à jour
	 * @param cronExpression la nouvelle expression Cron à utiliser
	 * @param multiExecution le processus autorise-t-il la multi execution
	 * @param rescuePeriod la nouvelle durée de validité d'une planification
	 * @param active le processus est-il actif
	 */
	void updateProcessDefinitionProperties(String processName, Optional<String> cronExpression, boolean multiExecution, int rescuePeriod, boolean active);

	/**
	 * Met à jour les paramètres initiaux d'exécution d'un processus
	 * @param processName le nom du processus à mettre à jour
	 * @param initialParams the params used to start the first activity
	 */
	void updateProcessDefinitionInitialParams(String processName, Map<String, String> initialParams);

}
