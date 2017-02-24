package io.vertigo.orchestra.plugins.definition.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.vertigo.app.Home;
import io.vertigo.core.definition.DefinitionSpace;
import io.vertigo.core.definition.DefinitionSpaceWritable;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.impl.definition.ProcessDefinitionStorePlugin;

/**
 * Plugin de gestion des définitions en mémoire dans le DefinitionSpace.
 * @author mlaroche
 *
 */
public class MemoryProcessDefinitionStorePlugin implements ProcessDefinitionStorePlugin {
	private DefinitionSpace getDefinitionSpace() {
		return Home.getApp().getDefinitionSpace();
	}

	@Override
	public void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition) {
		((DefinitionSpaceWritable) getDefinitionSpace()).registerDefinition(processDefinition);

	}

	@Override
	public boolean processDefinitionExists(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return getDefinitionSpace().contains(processName);
	}

	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return getDefinitionSpace().resolve(processName, ProcessDefinition.class);
	}

	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		return new ArrayList<>(getDefinitionSpace().getAll(ProcessDefinition.class));
	}

	@Override
	public void updateProcessDefinitionProperties(final ProcessDefinition processDefinition, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod,
			final boolean active) {
		// unsupported
		throw new UnsupportedOperationException("An in memory definition is immutable");
	}

	@Override
	public void updateProcessDefinitionInitialParams(final ProcessDefinition processDefinition, final Map<String, String> initialParams) {
		throw new UnsupportedOperationException("An in memory definition is immutable");
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.UNSUPERVISED;
	}

}
