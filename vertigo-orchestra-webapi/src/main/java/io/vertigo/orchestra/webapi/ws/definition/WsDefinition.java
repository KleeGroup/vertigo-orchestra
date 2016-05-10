package io.vertigo.orchestra.webapi.ws.definition;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
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
	public OProcessUi getProcessById(@PathParam("id") final Long id) {
		return definitionServices.getProcessDefinitionById(id);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@POST("search")
	@AnonymousAccessAllowed
	public DtList<OProcessUi> searchProcessByLabel(@InnerBodyParam("criteria") final String criteria) {
		//TODO voir comment faire autrement
		if ("*".equals(criteria)) {
			return definitionServices.searchProcess("");
		}
		return definitionServices.searchProcess(criteria);
	}

	/**
	 * Get the processDefinition by Id
	 */
	@GET("{id}/activities")
	@AnonymousAccessAllowed
	public DtList<OActivity> getActivitiesByProcessId(@PathParam("id") final Long id) {
		return definitionServices.getActivitiesByProId(id);
	}

	/**
	 * Update the process properties
	 */
	@POST("{id}/updateProperties")
	@AnonymousAccessAllowed
	public void updateProcessProperties(@PathParam("id") final Long id, @InnerBodyParam("cronExpression") final Option<String> cronExpression, @InnerBodyParam("multiexecution") final boolean multiExecution, @InnerBodyParam("rescuePeriod") final Long rescuePerdiod, @InnerBodyParam("active") final boolean active) {
		definitionServices.updateProcessProperties(id, cronExpression, multiExecution, rescuePerdiod, active);
	}

	/**
	 * Update the process properties
	 */
	@POST("{id}/updateInitialParams")
	@AnonymousAccessAllowed
	public void updateProcessProperties(@PathParam("id") final Long id, @InnerBodyParam("initialParams") final Option<String> initialParams) {
		definitionServices.updateProcessInitialParams(id, initialParams);
	}
}
