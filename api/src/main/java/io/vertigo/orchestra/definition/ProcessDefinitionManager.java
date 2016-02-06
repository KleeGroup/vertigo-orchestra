package io.vertigo.orchestra.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Component;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends Component {

	DtList<OProcess> getRecurrentProcesses();

	OTask getFirtTaskByProcess(Long proId);

	Option<OTask> getNextTaskByTskId(Long tskId);

	//-----

	void createDefinition(ProcessDefinition processDefinition);
}
