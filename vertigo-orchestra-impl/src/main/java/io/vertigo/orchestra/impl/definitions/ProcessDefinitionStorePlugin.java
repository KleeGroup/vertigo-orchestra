package io.vertigo.orchestra.impl.definitions;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessType;

/**
 * Plugin de gestion des définitions de processus.
 * @author mlaroche
 *
 */
public interface ProcessDefinitionStorePlugin extends Plugin {

	/**
	 * @see io.vertigo.orchestra.definitions.OrchestraDefinitionManager#createOrUpdateDefinitionIfNeeded(ProcessDefinition)
	 */
	void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition);

	/**
	 * Retourne si une definition existe
	 * @param processName le nom du processus
	 * @return vrai si la definition existe
	 */
	boolean processDefinitionExists(final String processName);

	/**
	 * @see io.vertigo.orchestra.definitions.OrchestraDefinitionManager#getProcessDefinition(String)
	 */
	ProcessDefinition getProcessDefinition(final String processName);

	/**
	 * @see io.vertigo.orchestra.definitions.OrchestraDefinitionManager#getAllProcessDefinitions()
	 */
	List<ProcessDefinition> getAllProcessDefinitions();

	/**
	 * @see io.vertigo.orchestra.definitions.OrchestraDefinitionManager#updateProcessDefinitionProperties(String, Optional, boolean, int, boolean)
	 */
	void updateProcessDefinitionProperties(final ProcessDefinition processDefinition, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod,
			final boolean active);

	/**
	 * @see io.vertigo.orchestra.definitions.OrchestraDefinitionManager#updateProcessDefinitionInitialParams(String, Map)
	 */
	void updateProcessDefinitionInitialParams(final ProcessDefinition processDefinition, final Map<String, String> initialParams);

	/**
	 * Retourne le type de processus géré par le plugin
	 * @return le type de processus géré
	 */
	ProcessType getHandledProcessType();

}
