package io.vertigo.orchestra.impl.definition;

import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;

public interface ProcessDefinitionStorePlugin extends Plugin {

	void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition);

	boolean processDefinitionExists(final String processName);

	ProcessDefinition getProcessDefinition(final String processName);

	List<ProcessDefinition> getAllProcessDefinitions();

	void updateProcessDefinitionProperties(final ProcessDefinition processDefinition, final Optional<String> cronExpression, final boolean multiExecution, final int rescuePeriod,
			final boolean active);

	void updateProcessDefinitionInitialParams(final ProcessDefinition processDefinition, final Optional<String> initialParams);

	ProcessType getHandledProcessType();

}
