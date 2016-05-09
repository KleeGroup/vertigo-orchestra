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

	void createDefinition(ProcessDefinition processDefinition);

	void createOrUpdateDefinition(ProcessDefinition processDefinition);

	void updateProcessDefinitionProperties(String processName, Option<String> cronExpression, boolean multiExecution, Long rescuePeriod, boolean active);

	void updateProcessDefinitionInitialParams(String processName, Option<String> initialParams);

	void updateDefinition(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	List<ProcessDefinition> getAllProcessDefinitions();

	boolean processDefinitionExist(String processName);
}
