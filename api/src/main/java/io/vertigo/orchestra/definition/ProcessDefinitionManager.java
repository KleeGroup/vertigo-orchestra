package io.vertigo.orchestra.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessDefinitionManager extends OrchestraServices {

	DtList<OProcess> getActiveProcesses();

	void saveProcess(OProcess process);

	OTask getFirtTaskByProcess(Long proId);

	//-----
	void saveTask(OTask task);
}
