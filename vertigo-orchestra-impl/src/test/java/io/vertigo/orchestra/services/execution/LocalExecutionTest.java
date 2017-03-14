package io.vertigo.orchestra.services.execution;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessType;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class LocalExecutionTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private OrchestraServices orchestraServices;
	@Inject
	private OrchestraDefinitionManager orchestraDefinitionManager;

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void singleExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_UNSUPERVISED_MANUAL", "PRO_TEST_UNSUPERVISED_MANUAL", ProcessType.UNSUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		// We plan right now
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 12);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void recurrentExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_UNSUPERVISED_SCHEDULED", "PRO_TEST_UNSUPERVISED_SCHEDULED", ProcessType.UNSUPERVISED)
				.withCronExpression("*/15 * * * * ?")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		// We plan right now
		orchestraServices.getScheduler()
				.scheduleWithCron(processDefinition);

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 10);

	}

}
