package io.vertigo.orchestra.webapi.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;

/**
 * Service for access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface DefinitionServices extends StoreServices {

	OProcess getProcessDefinitionById(Long proId);

	DtList<OActivity> getActivitiesByProId(Long proId);

}
