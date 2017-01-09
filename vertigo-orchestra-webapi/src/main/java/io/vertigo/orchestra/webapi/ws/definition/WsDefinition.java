package io.vertigo.orchestra.webapi.ws.definition;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
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
	private ProcessDefinitionManager definitionManager;

	/**
	 * Retourne un processus par son id.
	 * @param processName l'id du processus
	 * @return le processus
	 */
	@GET("/{processName}")
	public ProcessDefinition getProcessById(@PathParam("processName") final String processName) {
		return definitionManager.getProcessDefinition(processName);
	}

	/**
	 * Retourne la liste des processus correspondant à un critère de recherche.
	 * @param criteria le critère
	 * @return la liste de resultats
	 */
	@POST("/search")
	public List<ProcessDefinition> searchProcessByLabel(@InnerBodyParam("criteria") final String criteria) {
		//TODO voir comment faire autrement
		final List<ProcessDefinition> definitions = definitionManager.getAllProcessDefinitions();
		if ("*".equals(criteria)) {
			return definitions;
		}
		return definitions
				.stream()
				.filter(definition -> definition.getLabel().startsWith(criteria))
				.collect(Collectors.toList());
	}

	/**
	 * Met à jour les propriétés d'un processus.
	 * @param processName l'id du processus à mettre à jour
	 * @param cronExpression la nouvelle expression cron de récurrence
	 * @param multiExecution le processus autorise-t-il la multi-exécution
	 * @param rescuePerdiodSeconds le temps de validité d'une planification
	 * @param active si le processus est actif
	 * @return le processus mis à jour
	 */
	@POST("/{processName}/updateProperties")
	public ProcessDefinition updateProcessProperties(@PathParam("processName") final String processName, @InnerBodyParam("cronExpression") final Optional<String> cronExpression,
			@InnerBodyParam("multiExecution") final boolean multiExecution, @InnerBodyParam("rescuePeriod") final int rescuePerdiodSeconds, @InnerBodyParam("active") final boolean active) {

		definitionManager.updateProcessDefinitionProperties(processName, cronExpression, multiExecution, rescuePerdiodSeconds, active);
		return definitionManager.getProcessDefinition(processName);
	}

	/**
	 * Mets à jour les paramètres initiaux de démarrage d'un processus
	 * @param processName l'id du processus à mettre à jour
	 * @param initialParams les nouveaux paramètres à utiliser (JSON sous forme de string)
	 * @return le processus mis à jour
	 */
	@POST("/{processName}/updateInitialParams")
	public ProcessDefinition updateInitialParams(@PathParam("processName") final String processName, @InnerBodyParam("initialParams") final Map<String, String> initialParams) {
		definitionManager.updateProcessDefinitionInitialParams(processName, initialParams);
		return definitionManager.getProcessDefinition(processName);
	}
}
