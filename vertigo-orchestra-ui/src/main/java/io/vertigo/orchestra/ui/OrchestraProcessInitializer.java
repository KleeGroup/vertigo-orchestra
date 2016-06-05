package io.vertigo.orchestra.ui;

import javax.inject.Inject;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;

/**
 * Initialisation des processus gérés par Orchestra
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OrchestraProcessInitializer implements ComponentInitializer {

	@Inject
	private OrchestraManager orchestraManager;

	/** {@inheritDoc} */
	@Override
	public void init() {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_CENTRE_FIN", "CHORUS - Centre financiers")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition);

		final ProcessDefinition processDefinition2 = new ProcessDefinitionBuilder("CHORUS_DOMAINES_FONC", "CHORUS - Domaines fonctionnels")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition2);

		final ProcessDefinition processDefinition3 = new ProcessDefinitionBuilder("CHORUS_CENTRE_COUT", "CHORUS - Centres de couts")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition3);

		final ProcessDefinition processDefinition4 = new ProcessDefinitionBuilder("CHORUS_REF_ACT", "CHORUS - Référentiels d'activités")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition4);

		final ProcessDefinition processDefinition5 = new ProcessDefinitionBuilder("CHORUS_EOTP", "CHORUS - éOTP")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition5);

		final ProcessDefinition processDefinition6 = new ProcessDefinitionBuilder("CHORUS_TRANCHES_FONC", "CHORUS - Tranches fonctionnelles")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition6);

		final ProcessDefinition processDefinition7 = new ProcessDefinitionBuilder("CHORUS_OPERATEURS_ECO", "CHORUS - Opérateurs économiques")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Opérateurs économiques\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition7);

		final ProcessDefinition processDefinition8 = new ProcessDefinitionBuilder("INFODAF_SERVICES", "INFODAF - Service fait")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Suivi des données financières\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition8);

		final ProcessDefinition processDefinition9 = new ProcessDefinitionBuilder("INFODAF_BDC", "INFODAF - Bons de commande")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Suivi des données financières\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition9);

		final ProcessDefinition processDefinition10 = new ProcessDefinitionBuilder("PLACE_CREATION", "PLACE - Création des consultations")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Consultation\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition10);

		final ProcessDefinition processDefinition11 = new ProcessDefinitionBuilder("PLACE_DCE", "PLACE - Récupération du DCE")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Consultation\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition11);

		final ProcessDefinition processDefinition12 = new ProcessDefinitionBuilder("DECLIC_PPAA", "DECLIC - Plans de charge")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Expression des besoins\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition12);

		final ProcessDefinition processDefinition13 = new ProcessDefinitionBuilder("DECLIC_FEB", "DECLIC - Fiche d'expression de besoins")
				.withCronExpression("0 */1 * * * ?")
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.withMetadatas("{\"functionalDomain\" : \"Expression des besoins\"}")
				.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
				.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
				.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
				.build();

		orchestraManager.createOrUpdateDefinitionIfNeeded(processDefinition13);

	}

}
