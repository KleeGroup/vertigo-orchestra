package io.vertigo.orchestra.services.definition;

import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface TaskServices extends OrchestraServices {

	public void saveTask(OTask task);

}
