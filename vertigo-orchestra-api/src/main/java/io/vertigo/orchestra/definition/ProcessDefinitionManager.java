package io.vertigo.orchestra.definition;

import java.util.List;

import io.vertigo.lang.Component;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Component {

	void createDefinition(Process processDefinition);

	Process getProcessDefinition(String processName);

	List<Process> getAllProcessDefinitions();
}
