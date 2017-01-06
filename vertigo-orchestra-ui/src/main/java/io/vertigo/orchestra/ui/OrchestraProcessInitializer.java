package io.vertigo.orchestra.ui;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.definition.ProcessType;

/**
 * Initialisation des processus gérés par Orchestra
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OrchestraProcessInitializer implements ComponentInitializer {

	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	/** {@inheritDoc} */
	@Override
	public void init() {

		final Map<String, String> initialParams = new HashMap<>();
		initialParams.put("filePath", "toto/titi");

		final Map<String, String> metaDatas = new HashMap<>();
		metaDatas.put("functionalDomain", "Référentiels");

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_CENTRE_FIN", "CHORUS - Centre financiers")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);

		final ProcessDefinition processDefinition2 = new ProcessDefinitionBuilder("CHORUS_DOMAINES_FONC", "CHORUS - Domaines fonctionnels")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition2);

		final ProcessDefinition processDefinition3 = new ProcessDefinitionBuilder("CHORUS_CENTRE_COUT", "CHORUS - Centres de couts")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition3);

		final ProcessDefinition processDefinition4 = new ProcessDefinitionBuilder("CHORUS_REF_ACT", "CHORUS - Référentiels d'activités")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition4);

		final ProcessDefinition processDefinition5 = new ProcessDefinitionBuilder("CHORUS_EOTP", "CHORUS - éOTP")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition5);

		final ProcessDefinition processDefinition6 = new ProcessDefinitionBuilder("CHORUS_TRANCHES_FONC", "CHORUS - Tranches fonctionnelles")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition6);

		final ProcessDefinition processDefinition7 = new ProcessDefinitionBuilder("CHORUS_OPERATEURS_ECO", "CHORUS - Opérateurs économiques")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition7);

		final ProcessDefinition processDefinition8 = new ProcessDefinitionBuilder("INFODAF_SERVICES", "INFODAF - Service fait")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition8);

		final ProcessDefinition processDefinition9 = new ProcessDefinitionBuilder("INFODAF_BDC", "INFODAF - Bons de commande")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition9);

		final ProcessDefinition processDefinition10 = new ProcessDefinitionBuilder("PLACE_CREATION", "PLACE - Création des consultations")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition10);

		final ProcessDefinition processDefinition11 = new ProcessDefinitionBuilder("PLACE_DCE", "PLACE - Récupération du DCE")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition11);

		final ProcessDefinition processDefinition12 = new ProcessDefinitionBuilder("DECLIC_PPAA", "DECLIC - Plans de charge")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition12);

		final ProcessDefinition processDefinition13 = new ProcessDefinitionBuilder("DECLIC_FEB", "DECLIC - Fiche d'expression de besoins")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class)
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class)
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class)
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition13);

		final ProcessDefinition processDefinition14 = new ProcessDefinitionBuilder("PRO_IN_MEMORY", "En mémoire", ProcessType.UNSUPERVISED)
				.withInitialParams(initialParams)
				.withMetadatas(metaDatas)
				.addActivity("ACT_1", "Complet", EmptyActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition14);
	}

}
