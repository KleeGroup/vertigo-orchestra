package io.vertigo.orchestra.definition;

import javax.inject.Inject;

import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessServicesTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private ProcessDefinitionManager processServices;

	/**
	 *
	 */
	@Test
	public void saveNewProcess() {
		final OProcess process = new OProcess();

		process.setName("TEST");
		process.setPrtCd("EXCHANGE");
		process.setTrtCd("RECURRENT");
		process.setDelay(1000L);

		processServices.saveProcess(process);
		System.out.println(process.getProId());

	}

}
