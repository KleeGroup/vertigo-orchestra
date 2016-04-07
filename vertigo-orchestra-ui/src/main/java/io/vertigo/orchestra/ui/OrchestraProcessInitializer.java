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

	@Inject
	private OrchestraManager orchestraManager;

	/** {@inheritDoc} */
	@Override
	public void init() {

		if (!orchestraManager.processDefinitionExist(TEST_PROCESS_NAME)) {
			final ProcessDefinition processDefinition = new ProcessDefinitionBuilder(TEST_PROCESS_NAME, TEST_PROCESS_LABEL)
					.withCron("0 */1 * * * ?")
					.addActivity("DUMB ACTIVITY 1 1", "Activité vide", EmptyActivityEngine.class.getName())
					.addActivity("DUMB ACTIVITY 1 2", "Activité vide 2", EmptyActivityEngine.class.getName())
					.build();

			orchestraManager.createDefinition(processDefinition);
		}

	}

}
