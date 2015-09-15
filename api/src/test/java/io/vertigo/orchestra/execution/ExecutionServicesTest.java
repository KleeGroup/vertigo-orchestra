package io.vertigo.orchestra.execution;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Test;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.definition.TaskServices;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.process.planner.ProcessPlannerManager;
import io.vertigo.orchestra.services.ApplicationServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ExecutionServicesTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private ProcessPlannerManager processPlanificationServices;
	@Inject
	private ProcessDefinitionManager processServices;
	@Inject
	private TaskServices taskServices;

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
		processServices.saveProcess(process);

		final OTask task = new OTask();
		task.setName("DUMB TASK");
		task.setMilestone(false);
		task.setEngine("io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine");
		task.setProId(process.getProId());
		taskServices.saveTask(task);

		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
		processPlanificationServices.plannProcessAt(process.getProId(), new Date());
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
		processServices.saveProcess(process);

		final OTask task = new OTask();
		task.setName("DUMB TASK");
		task.setMilestone(false);
		task.setEngine("io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine");
		task.setProId(process.getProId());
		taskServices.saveTask(task);

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
