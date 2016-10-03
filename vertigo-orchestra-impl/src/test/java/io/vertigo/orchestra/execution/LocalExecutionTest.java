package io.vertigo.orchestra.execution;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class LocalExecutionTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private ProcessSchedulerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void singleExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_UNSUPERVISED", "PRO_TEST_UNSUPERVISED", ProcessType.UNSUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);

		// We plan right now
		processPlannerManager.scheduleAt(processDefinition, new Date(), Optional.<String> empty());

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 12);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void recurrentExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_UNSUPERVISED", "PRO_TEST_UNSUPERVISED", ProcessType.UNSUPERVISED)
				.withCronExpression("*/15 * * * * ?")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);

		// We plan right now
		processPlannerManager.scheduleWithCron(processDefinition);

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 60);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		// Nothing in this case
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doTearDown() throws Exception {
		// Nothing in this case
	}

}
