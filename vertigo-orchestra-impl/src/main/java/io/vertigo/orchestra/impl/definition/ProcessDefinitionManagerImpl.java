package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.definition.ProcessType;

/**
 * Implémentation du manager des définitions de processus Orchestra.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessDefinitionManagerImpl implements ProcessDefinitionManager {

	private final Map<ProcessType, ProcessDefinitionStorePlugin> storePluginsMap = new HashMap<>();

	@Inject
	public ProcessDefinitionManagerImpl(final List<ProcessDefinitionStorePlugin> definitionStorePlugins) {
		Assertion.checkState(definitionStorePlugins.size() > 0, "At least one ProcessDefinitionStorePlugin is required");
		// ---
		for (final ProcessDefinitionStorePlugin storePlugin : definitionStorePlugins) {
			Assertion.checkState(!storePluginsMap.containsKey(storePlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}", storePlugin.getHandledProcessType());
			storePluginsMap.put(storePlugin.getHandledProcessType(), storePlugin);
		}
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return storePluginsMap.values()
				.stream()
				.filter(processDefinitionStorePlugin -> processDefinitionStorePlugin.processDefinitionExists(processName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Cannot find process with name " + processName))
				.getProcessDefinition(processName);
	}

	/** {@inheritDoc} */
	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		final List<ProcessDefinition> processDefinitions = new ArrayList<>();
		for (final ProcessDefinitionStorePlugin storePlugin : storePluginsMap.values()) {
			processDefinitions.addAll(storePlugin.getAllProcessDefinitions());
		}
		return Collections.unmodifiableList(processDefinitions);

	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		final ProcessDefinitionStorePlugin storePlugin = storePluginsMap.get(processDefinition.getProcessType());
		Assertion.checkNotNull(storePlugin, "No plugin found for managing processType {0}", processDefinition.getProcessType());
		// ---
		storePlugin.createOrUpdateDefinitionIfNeeded(processDefinition);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionProperties(final String processName, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod, final boolean active) {
		Assertion.checkArgNotEmpty(processName);
		Assertion.checkNotNull(cronExpression);
		Assertion.checkNotNull(rescuePeriod);
		// ---
		final ProcessDefinition processDefinition = getProcessDefinition(processName);
		getPluginByType(processDefinition.getProcessType()).updateProcessDefinitionProperties(processDefinition, cronExpression, multiExecution, rescuePeriod, active);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessDefinitionInitialParams(final String processName, final Optional<String> initialParams) {
		Assertion.checkArgNotEmpty(processName);
		Assertion.checkNotNull(initialParams);
		// ---
		final ProcessDefinition processDefinition = getProcessDefinition(processName);
		getPluginByType(processDefinition.getProcessType()).updateProcessDefinitionInitialParams(processDefinition, initialParams);
	}

	private ProcessDefinitionStorePlugin getPluginByType(final ProcessType processType) {
		final ProcessDefinitionStorePlugin storePlugin = storePluginsMap.get(processType);
		Assertion.checkNotNull(storePlugin, "No plugin found for managing processType {0}", processType.name());
		return storePlugin;
	}

}
