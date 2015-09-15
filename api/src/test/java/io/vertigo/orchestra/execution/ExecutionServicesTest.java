package io.vertigo.orchestra.execution;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.orchestra.services.ApplicationServices;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ExecutionServicesTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private ProcessPlannerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Inject
	private ApplicationServices applicationServices;

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void clean() throws InterruptedException {
		// nothing
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void dumbexecution() throws InterruptedException {
		final OProcess process = new OProcess();
		process.setName("TEST RECURRENT");
		process.setDelay(100L);
		process.setPrtCd("DUMB");
		process.setTrtCd("DUMB");
		processDefinitionManager.saveProcess(process);

		final OTask task = new OTask();
		task.setName("DUMB TASK");
		task.setMilestone(false);
		task.setEngine("io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine");
		task.setProId(process.getProId());
		processDefinitionManager.saveTask(task);

		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		processPlannerManager.plannProcessAt(process.getProId(), new Date());
		Thread.sleep(1000 * 60);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void dumbRecurrentExecution() throws InterruptedException {
		final OProcess process = new OProcess();
		process.setName("TEST RECURRENT");
		process.setDelay(100L);
		process.setPrtCd("DUMB");
		process.setTrtCd("RECURRENT");
		processDefinitionManager.saveProcess(process);

		final OTask task = new OTask();
		task.setName("DUMB TASK");
		task.setMilestone(false);
		task.setEngine("io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine");
		task.setProId(process.getProId());
		processDefinitionManager.saveTask(task);

		Thread.sleep(1000 * 60);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		applicationServices.deleteAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doTearDown() throws Exception {
		// Nothing in this case
	}

}
