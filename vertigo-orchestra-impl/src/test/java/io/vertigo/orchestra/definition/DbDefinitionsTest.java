package io.vertigo.orchestra.definition;

import java.util.Optional;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 */
public class DbDefinitionsTest extends AbstractOrchestraTestCaseJU4 {
	@Inject

	private ProcessDefinitionManager processDefinitionManager;

	@Test
	public void testRegister() {
		//Before : 0
		Assert.assertEquals(0, processDefinitionManager.getAllProcessDefinitionsByType(ProcessType.SUPERVISED).size());

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_BASIC", "TEST BASIC", ProcessType.SUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbErrorActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
		//After :1
		Assert.assertEquals(1, processDefinitionManager.getAllProcessDefinitionsByType(ProcessType.SUPERVISED).size());

		final ProcessDefinition processDefinition2 = processDefinitionManager.getProcessDefinition("TEST_BASIC");
		Assert.assertEquals(processDefinition.getName(), processDefinition2.getName());
	}
	//

	@Test
	public void testUpateInitialParams() {
		//Before : 0

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_BASIC", "TEST BASIC", ProcessType.SUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbErrorActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
		// no initialParams
		Assert.assertTrue(!processDefinitionManager.getProcessDefinition("TEST_BASIC").getInitialParams().isPresent());

		processDefinitionManager.updateProcessDefinitionInitialParams("TEST_BASIC", Optional.of("{\"filePath\" : \"toto/titi\"}"));
		// with initialParams
		Assert.assertTrue(processDefinitionManager.getProcessDefinition("TEST_BASIC").getInitialParams().isPresent());
	}

	@Test
	public void testUpateProperties() {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_UPDATE_CRON", "TEST_UPDATE_CRON", ProcessType.SUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbErrorActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
		// no initialParams
		Assert.assertTrue(!processDefinitionManager.getProcessDefinition("TEST_UPDATE_CRON").getCronExpression().isPresent());

		processDefinitionManager.updateProcessDefinitionProperties("TEST_UPDATE_CRON", Optional.of("*/15 * * * * ?"), processDefinition.isMultiExecution(), processDefinition.getRescuePeriod(),
				processDefinition.isActive());
		// with initialParams
		Assert.assertTrue(processDefinitionManager.getProcessDefinition("TEST_UPDATE_CRON").getCronExpression().isPresent());
	}
}
