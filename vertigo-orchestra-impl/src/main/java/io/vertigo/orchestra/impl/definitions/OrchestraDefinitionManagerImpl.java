package io.vertigo.orchestra.impl.definitions;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessType;

/**
 * Implémentation du manager des définitions de processus Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OrchestraDefinitionManagerImpl implements OrchestraDefinitionManager {
	private final Map<ProcessType, ProcessDefinitionStorePlugin> processDefinitionStorePluginsByProcessType = new EnumMap<>(ProcessType.class);

	/**
	 * Constructeur du gestionnaire de définitions.
	 * @param processDefinitionStorePlugins la liste des plugins gérant des définitions de processus
	 */
	@Inject
	public OrchestraDefinitionManagerImpl(final List<ProcessDefinitionStorePlugin> processDefinitionStorePlugins) {
		Assertion.checkNotNull(processDefinitionStorePlugins);
		Assertion.checkState(!processDefinitionStorePlugins.isEmpty(), "At least one ProcessDefinitionStorePlugin is required");
		// ---
		for (final ProcessDefinitionStorePlugin storePlugin : processDefinitionStorePlugins) {
			Assertion.checkState(!processDefinitionStorePluginsByProcessType.containsKey(storePlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}",
					storePlugin.getHandledProcessType());
			processDefinitionStorePluginsByProcessType.put(storePlugin.getHandledProcessType(), storePlugin);
		}
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return processDefinitionStorePluginsByProcessType.values()
				.stream()
				.filter(processDefinitionStorePlugin -> processDefinitionStorePlugin.processDefinitionExists(processName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Cannot find process with name " + processName))
				.getProcessDefinition(processName);
	}

	/** {@inheritDoc} */
	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		return processDefinitionStorePluginsByProcessType.values()
				.stream()
				.flatMap(processDefinitionStorePlugin -> processDefinitionStorePlugin.getAllProcessDefinitions().stream())
				.collect(Collectors.toList());
	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		//---
		final ProcessDefinitionStorePlugin storePlugin = processDefinitionStorePluginsByProcessType.get(processDefinition.getProcessType());
		Assertion.checkNotNull(storePlugin, "No plugin found for managing processType {0}", processDefinition.getProcessType());
		// ---
		storePlugin.createOrUpdateDefinition(processDefinition);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionProperties(
			final String processName,
			final Optional<String> cronExpression,
			final boolean multiExecution,
			final int rescuePeriod,
			final boolean active) {
		Assertion.checkArgNotEmpty(processName);
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(rescuePeriod);
		//---
		final ProcessDefinition processDefinition = getProcessDefinition(processName);
		getPluginByType(processDefinition.getProcessType()).updateProcessDefinitionProperties(processDefinition, cronExpression, multiExecution, rescuePeriod, active);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionInitialParams(final String processName, final Map<String, String> initialParams) {
		Assertion.checkArgNotEmpty(processName);
		Assertion.checkNotNull(initialParams);
		//---
		final ProcessDefinition processDefinition = getProcessDefinition(processName);
		getPluginByType(processDefinition.getProcessType()).updateProcessDefinitionInitialParams(processDefinition, initialParams);
	}

	private ProcessDefinitionStorePlugin getPluginByType(final ProcessType processType) {
		final ProcessDefinitionStorePlugin storePlugin = processDefinitionStorePluginsByProcessType.get(processType);
		Assertion.checkNotNull(storePlugin, "No plugin found for managing processType {0}", processType.name());
		return storePlugin;
	}

	@Override
	public List<ProcessDefinition> getAllProcessDefinitionsByType(final ProcessType processType) {
		Assertion.checkNotNull(processType);
		//---
		return getPluginByType(processType).getAllProcessDefinitions();
	}

}
