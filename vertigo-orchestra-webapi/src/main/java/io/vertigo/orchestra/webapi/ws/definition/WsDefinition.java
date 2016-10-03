package io.vertigo.orchestra.webapi.ws.definition;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
import io.vertigo.vega.webservice.WebServices;
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
@PathPrefix("/orchestra/definitions")
public class WsDefinition implements WebServices {

	@Inject
	private DefinitionServices definitionServices;

	/**
	 * Retourne un processus par son id.
	 * @param id l'id du processus
	 * @return le processus
	 */
	@GET("/{id}")
	public OProcessUi getProcessById(@PathParam("id") final Long id) {
		return definitionServices.getProcessDefinitionById(id);
	}

	/**
	 * Retourne la liste des processus correspondant à un critère de recherche.
	 * @param criteria le critère
	 * @return la liste de resultats
	 */
	@POST("/search")
	public DtList<OProcessUi> searchProcessByLabel(@InnerBodyParam("criteria") final String criteria) {
		//TODO voir comment faire autrement
		if ("*".equals(criteria)) {
			return definitionServices.searchProcess("");
		}
		return definitionServices.searchProcess(criteria);
	}

	/**
	 * Retourne lq liste des activités d'un processus par son id.
	 * @param id du processus
	 * @return la liste des activités
	 */
	@GET("/{id}/activities")
	public DtList<OActivity> getActivitiesByProcessId(@PathParam("id") final Long id) {
		return definitionServices.getActivitiesByProId(id);
	}

	/**
	 * Met à jour les propriétés d'un processus.
	 * @param id l'id du processus à mettre à jour
	 * @param cronExpression la nouvelle expression cron de récurrence
	 * @param multiExecution le processus autorise-t-il la multi-exécution
	 * @param rescuePerdiodSeconds le temps de validité d'une planification
	 * @param active si le processus est actif
	 * @return le processus mis à jour
	 */
	@POST("/{id}/updateProperties")
	public OProcessUi updateProcessProperties(@PathParam("id") final Long id, @InnerBodyParam("cronExpression") final Optional<String> cronExpression,
			@InnerBodyParam("multiexecution") final boolean multiExecution, @InnerBodyParam("rescuePeriod") final int rescuePerdiodSeconds, @InnerBodyParam("active") final boolean active) {
		definitionServices.updateProcessProperties(id, cronExpression, multiExecution, rescuePerdiodSeconds, active);
		return definitionServices.getProcessDefinitionById(id);
	}

	/**
	 * Mets à jour les paramètres initiaux de démarrage d'un processus
	 * @param id l'id du processus à mettre à jour
	 * @param initialParams les nouveaux paramètres à utiliser (JSON sous forme de string)
	 * @return le processus mis à jour
	 */
	@POST("/{id}/updateInitialParams")
	public OProcessUi updateInitialParams(@PathParam("id") final Long id, @InnerBodyParam("initialParams") final Optional<String> initialParams) {
		definitionServices.updateProcessInitialParams(id, initialParams);
		return definitionServices.getProcessDefinitionById(id);
	}
}
