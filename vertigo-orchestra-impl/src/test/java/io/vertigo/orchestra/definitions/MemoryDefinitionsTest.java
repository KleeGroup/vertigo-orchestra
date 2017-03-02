package io.vertigo.orchestra.definitions;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessType;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 */
public class MemoryDefinitionsTest extends AbstractOrchestraTestCaseJU4 {
	@Inject

	private OrchestraDefinitionManager orchestraDefinitionManager;

	@Test
	public void testRegister() {
		//Before : 0
		Assert.assertEquals(0, orchestraDefinitionManager.getAllProcessDefinitionsByType(ProcessType.UNSUPERVISED).size());

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_BASIC", "TEST BASIC", ProcessType.UNSUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);
		//After :1
		Assert.assertEquals(1, orchestraDefinitionManager.getAllProcessDefinitionsByType(ProcessType.UNSUPERVISED).size());

		final ProcessDefinition processDefinition2 = orchestraDefinitionManager.getProcessDefinition("PRO_TEST_BASIC");
		Assert.assertEquals(processDefinition.getName(), processDefinition2.getName());
	}
	//
}
