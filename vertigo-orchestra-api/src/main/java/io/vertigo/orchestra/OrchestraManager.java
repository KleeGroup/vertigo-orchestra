package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Manager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.execution.ExecutionState;

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
	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

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
	List<ProcessDefinition> getAllProcesses();

	/**
	 * Programme maintenant une execution d'un processus donné.
	 * @param processName Le nom du processus à lancer
	 * @param initialParams Les paramètres initiaux à utiliser (JSON sous forme de string)
	 */
	void scheduleNow(String processName, Optional<String> initialParams);

	/**
	 * Programme à une date donnée une execution d'un processus donné.
	 * @param processName Le nom du processus à lancer
	 * @param expectedTime La date à laquelle il doit être lancé
	 * @param initialParams Les paramètres initiaux à utiliser (JSON sous forme de string)
	 */
	void scheduleAt(String processName, Date expectedTime, Optional<String> initialParams);

	/**
	 * Termine une activité mise en attente.
	 * @param activityExecutionId L'activité à terminer
	 * @param token le token de securité
	 * @param state L'état final de l'exécution
	 */
	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);
}
