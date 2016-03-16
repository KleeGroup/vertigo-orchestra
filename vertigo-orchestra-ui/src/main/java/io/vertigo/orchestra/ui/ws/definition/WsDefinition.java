package io.vertigo.orchestra.ui.ws.definition;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.ui.services.DefinitionServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathParam;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

/**
 * WebService API for managing ProcessDefinitions
 * @author mlaroche.
 * @version $Id$
 */
@PathPrefix("/definitions/")
public class WsDefinition implements WebServices {

	@Inject
	private DefinitionServices definitionServices;

	/**
	 * Get the processDefinition by Id
	 */
	@GET("{id}")
	@AnonymousAccessAllowed
	public OProcess getProcessById(@PathParam("id") final Long id) {
		return definitionServices.getProcessDefinitionById(id);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("{id}/activities")
	@AnonymousAccessAllowed
	public DtList<OActivity> getActivitiesByProcessId(@PathParam("id") final Long id) {
		return definitionServices.getActivitiesByProId(id);
	}
}
