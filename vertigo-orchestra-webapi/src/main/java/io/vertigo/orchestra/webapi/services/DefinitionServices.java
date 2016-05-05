package io.vertigo.orchestra.webapi.services;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;

/**
 * Service for access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface DefinitionServices extends StoreServices {

	OProcessUi getProcessDefinitionById(Long proId);

	DtList<OActivity> getActivitiesByProId(Long proId);

	DtList<OProcessUi> searchProcess(String search);

}
