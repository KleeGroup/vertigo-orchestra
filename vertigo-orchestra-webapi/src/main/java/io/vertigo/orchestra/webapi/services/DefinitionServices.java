package io.vertigo.orchestra.webapi.services;

import java.util.Optional;

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

	/**
	 * Récupère une définition par son id sous forme d'objet de présentation.
	 * @param proId l'id
	 * @return la définition
	 */
	OProcessUi getProcessDefinitionById(Long proId);

	/**
	 * Récupère la liste des activités composant un processus.
	 * @param proId l'id du processus
	 * @return la liste des activités
	 */
	DtList<OActivity> getActivitiesByProId(Long proId);

	/**
	 * Recherche un processus par son nom (recherche de type contient)
	 * @param search la terme à rechercher
	 * @return la liste des processus répondant au critère de recherche
	 */
	DtList<OProcessUi> searchProcess(String search);

	/**
	 * Met à jour les propriétés d'un processus.
	 * @param id le processus à mettre à jour
	 * @param cronExpression la nouvelle expression cron à utiliser
	 * @param multiExecution le processus autorise-t-il la multi execution
	 * @param rescuePeriod la période de validité des planification
	 * @param active si le processus est actif
	 */
	void updateProcessProperties(Long id, Optional<String> cronExpression, boolean multiExecution, int rescuePeriod, boolean active);

	/**
	 * Met à jour les paramètre de démarrage initiaux d'un processus.
	 * @param id le processus à mettre à jour
	 * @param initialParams les nouveaux paramètre (JSON sous forme de string)
	 */
	void updateProcessInitialParams(Long id, Optional<String> initialParams);

}
