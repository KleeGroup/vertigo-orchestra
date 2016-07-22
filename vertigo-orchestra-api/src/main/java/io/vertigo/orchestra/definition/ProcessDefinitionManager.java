package io.vertigo.orchestra.definition;

import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Manager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Manager {
	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	void updateProcessDefinitionProperties(String processName, Optional<String> cronExpression, boolean multiExecution, int rescuePeriod, boolean active);

	void updateProcessDefinitionInitialParams(String processName, Optional<String> initialParams);

	List<ProcessDefinition> getAllProcessDefinitions();
}
