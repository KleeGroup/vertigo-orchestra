package io.vertigo.orchestra.definition;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 */
public class MemoryDefinitionsTest extends AbstractOrchestraTestCaseJU4 {
	@Inject

	private ProcessDefinitionManager processDefinitionManager;

	@Test
	public void testRegister() {
		//Before : 0
		Assert.assertEquals(0, processDefinitionManager.getAllProcessDefinitionsByType(ProcessType.UNSUPERVISED).size());

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("PRO_TEST_BASIC", "TEST BASIC", ProcessType.UNSUPERVISED)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbErrorActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
		//After :1
		Assert.assertEquals(1, processDefinitionManager.getAllProcessDefinitionsByType(ProcessType.UNSUPERVISED).size());

		final ProcessDefinition processDefinition2 = processDefinitionManager.getProcessDefinition("PRO_TEST_BASIC");
		Assert.assertEquals(processDefinition.getName(), processDefinition2.getName());
	}
	//
}
