/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2017, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
