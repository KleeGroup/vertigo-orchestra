package io.vertigo.orchestra.definitions;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessType;
import io.vertigo.util.MapBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 */
public class DbDefinitionsTest extends AbstractOrchestraTestCaseJU4 {
	@Inject

	private OrchestraDefinitionManager orchestraDefinitionManager;

	@Test
	public void testRegister() {

		//Before : 0
		Assert.assertEquals(0, orchestraDefinitionManager.getAllProcessDefinitionsByType(ProcessType.SUPERVISED).size());

		final Map<String, String> metadatas = new HashMap<>();
		metadatas.put("test", "toto");

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_BASIC", "TEST BASIC", ProcessType.SUPERVISED)
				.withMetadatas(metadatas)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);
		//After :1
		Assert.assertEquals(1, orchestraDefinitionManager.getAllProcessDefinitionsByType(ProcessType.SUPERVISED).size());

		final ProcessDefinition processDefinition2 = orchestraDefinitionManager.getProcessDefinition("TEST_BASIC");
		Assert.assertEquals(processDefinition.getName(), processDefinition2.getName());
		Assert.assertTrue(processDefinition2.getMetadatas().containsKey("test"));
	}
	//

	@Test
	public void testUpateInitialParams() {
		//Before : 0

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_BASIC", "TEST BASIC", ProcessType.SUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);
		// no initialParams
		Assert.assertTrue(orchestraDefinitionManager.getProcessDefinition("TEST_BASIC").getTriggeringStrategy().getInitialParams().isEmpty());

		orchestraDefinitionManager.updateProcessDefinitionInitialParams("TEST_BASIC", new MapBuilder<String, String>().put("filePath", "toto/titi").build());
		// with initialParams
		Assert.assertTrue(!orchestraDefinitionManager.getProcessDefinition("TEST_BASIC").getTriggeringStrategy().getInitialParams().isEmpty());
	}

	@Test
	public void testUpateProperties() {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_UPDATE_CRON", "TEST_UPDATE_CRON", ProcessType.SUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);
		// no initialParams
		Assert.assertTrue(!orchestraDefinitionManager.getProcessDefinition("TEST_UPDATE_CRON").getTriggeringStrategy().getCronExpression().isPresent());

		orchestraDefinitionManager.updateProcessDefinitionProperties("TEST_UPDATE_CRON", Optional.of("*/15 * * * * ?"), processDefinition.getTriggeringStrategy().isMultiExecution(),
				processDefinition.getTriggeringStrategy().getRescuePeriod(),
				processDefinition.isActive());
		// with initialParams
		Assert.assertTrue(orchestraDefinitionManager.getProcessDefinition("TEST_UPDATE_CRON").getTriggeringStrategy().getCronExpression().isPresent());
	}
}
