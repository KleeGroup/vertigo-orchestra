package io.vertigo.orchestra.impl.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.vertigo.app.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;

public class MemoryProcessDefinitionStorePlugin implements ProcessDefinitionStorePlugin {

	@Override
	public void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition) {
		Home.getApp().getDefinitionSpace().put(processDefinition);

	}

	@Override
	public boolean processDefinitionExists(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return Home.getApp().getDefinitionSpace().containsDefinitionName(processName);
	}

	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		return Home.getApp().getDefinitionSpace().resolve(processName, ProcessDefinition.class);
	}

	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		return new ArrayList<>(Home.getApp().getDefinitionSpace().getAll(ProcessDefinition.class));
	}

	@Override
	public void updateProcessDefinitionProperties(final ProcessDefinition processDefinition, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod,
			final boolean active) {
		// unsupported
		throw new UnsupportedOperationException("An in memory definition is immutable");
	}

	@Override
	public void updateProcessDefinitionInitialParams(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		// unsupported
		throw new UnsupportedOperationException("An in memory definition is immutable");
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.UNSUPERVISED;
	}

}
