package io.vertigo.orchestra.definition;

import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Component;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Component {
	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	void updateProcessDefinitionProperties(String processName, Optional<String> cronExpression, boolean multiExecution, int rescuePeriod, boolean active);

	void updateProcessDefinitionInitialParams(String processName, Optional<String> initialParams);

	List<ProcessDefinition> getAllProcessDefinitions();
}
