package io.vertigo.orchestra.services.definition;

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
public interface ProcessServices extends OrchestraServices {

	public DtList<OProcess> getActiveProcesses();

	public void saveProcess(OProcess process);

	public OTask getFirtTaskByProcess(Long proId);

}
