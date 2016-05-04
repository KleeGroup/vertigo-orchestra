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

	private static final String TEST_PROCESS_NAME = "TEST SCHEDULED";
	private static final String TEST_PROCESS_LABEL = "Processus Planifié de test";
	private static final String TEST_PROCESS_NAME_2 = "TEST SINGLE";
	private static final String TEST_PROCESS_LABEL_2 = "Processus simple de test";

	@Inject
	private OrchestraManager orchestraManager;

	/** {@inheritDoc} */
	@Override
	public void init() {

		if (!orchestraManager.processDefinitionExist(TEST_PROCESS_NAME)) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder(TEST_PROCESS_NAME, TEST_PROCESS_LABEL)
					.withCron("0 */5 * * * ?")
					.withInitialParams("{\"filePath\" : \"toto/titi\"}")
					.addActivity("DUMB ACTIVITY 1 1", "Activité vide", EmptyActivityEngine.class.getName())
					.addActivity("DUMB ACTIVITY 1 2", "Activité vide 2", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		if (!orchestraManager.processDefinitionExist(TEST_PROCESS_NAME_2)) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder(TEST_PROCESS_NAME_2, TEST_PROCESS_LABEL_2)
					.addActivity("DUMB ACTIVITY 1 1", "Activité vide", EmptyActivityEngine.class.getName())
					.addActivity("DUMB ACTIVITY 1 2", "Activité vide 2", CallAlphaActivity.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

		//		orchestraManager.scheduleNow(TEST_PROCESS_NAME_2, Option.<String> none());

	}

}
