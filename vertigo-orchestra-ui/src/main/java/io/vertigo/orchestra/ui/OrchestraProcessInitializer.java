package io.vertigo.orchestra.ui;

import javax.inject.Inject;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.impl.definition.ProcessDefinitionBuilder;

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

		if (!orchestraManager.processDefinitionExist("CHORUS_CENTRE_FIN")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_CENTRE_FIN", "CHORUS - Centre financiers")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_DOMAINES_FONC")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_DOMAINES_FONC", "CHORUS - Domaines fonctionnels")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_CENTRE_COUT")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_CENTRE_COUT", "CHORUS - Centres de couts")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_REF_ACT")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_REF_ACT", "CHORUS - Référentiels d'activités")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_EOTP")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_EOTP", "CHORUS - éOTP")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_TRANCHES_FONC")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_TRANCHES_FONC", "CHORUS - Tranches fonctionnelles")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Référentiels\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("CHORUS_OPERATEURS_ECO")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("CHORUS_OPERATEURS_ECO", "CHORUS - Opérateurs économiques")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Opérateurs économiques\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("INFODAF_SERVICES")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("INFODAF_SERVICES", "INFODAF - Service fait")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Suivi des données financières\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("INFODAF_BDC")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("INFODAF_BDC", "INFODAF - Bons de commande")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Suivi des données financières\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("PLACE_CREATION")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PLACE_CREATION", "PLACE - Création des consultations")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Consultation\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("PLACE_DCE")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PLACE_DCE", "PLACE - Récupération du DCE")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Consultation\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("DECLIC_PPAA")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("DECLIC_PPAA", "DECLIC - Plans de charge")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Expression des besoins\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist("DECLIC_FEB")) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("DECLIC_FEB", "DECLIC - Fiche d'expression de besoins")
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.withMetadatas("{\"functionalDomain\" : \"Expression des besoins\"}")
					.addActivity("ACT_1", "Récupération du fichier", EmptyActivityEngine.class.getName())
					.addActivity("ACT_2", "Intégration des données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_3", "Traitement sur les données", EmptyActivityEngine.class.getName())
					.addActivity("ACT_4", "Envoi à ALPHA", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

	}

}
