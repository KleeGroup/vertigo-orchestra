package io.vertigo.orchestra.webapi.ws.authentification;

import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.GET;
import io.vertigo.vega.webservice.stereotype.PathPrefix;
import io.vertigo.vega.webservice.stereotype.SessionInvalidate;

/**
 * WebService API for managing ProcessDefinitions
 * @author mlaroche.
 * @version $Id$
 */
@PathPrefix("/orchestra/authentification")
public class WsAuthentification implements WebServices {

	/**
	 * Get the processDefinition by Id
	 */
	@GET("/logout")
	@SessionInvalidate
	public void logout() {
		//nothing
	}

}
