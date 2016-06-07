package io.vertigo.orchestra.definition;

import java.util.List;

import io.vertigo.lang.Component;
import io.vertigo.lang.Option;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Component {
	void createOrUpdateDefinitionIfNeeded(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	void updateProcessDefinitionProperties(String processName, Option<String> cronExpression, boolean multiExecution, int rescuePeriod, boolean active);

	void updateProcessDefinitionInitialParams(String processName, Option<String> initialParams);

	List<ProcessDefinition> getAllProcessDefinitions();
}
