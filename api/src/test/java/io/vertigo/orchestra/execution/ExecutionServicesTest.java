package io.vertigo.orchestra.execution;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.metamodel.TaskDefinitionBuilder;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.dynamox.task.TaskEngineProc;
import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.util.ListBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ExecutionServicesTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private TaskManager taskManager;

	@Inject
	private ProcessPlannerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

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
		task.setEngine("io.vertigo.orchestra.execution.engine.DumbOTaskEngine");
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
		task.setEngine("io.vertigo.orchestra.execution.engine.DumbOTaskEngine");
		task.setProId(process.getProId());
		processDefinitionManager.saveTask(task);

		Thread.sleep(1000 * 60);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		//A chaque test on recrée la table famille
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final List<String> requests = new ListBuilder<String>()
					.add(" delete from o_process_planification;")
					.add(" delete from o_task_execution;")
					.add(" delete from o_process_execution;")
					.add(" delete from o_task;")
					.add(" delete from o_process;")
					.build();

			for (final String request : requests) {
				final TaskDefinition taskDefinition = new TaskDefinitionBuilder("TK_CLEAN")
						.withEngine(TaskEngineProc.class)
						.withRequest(request)
						.build();
				final Task task = new TaskBuilder(taskDefinition).build();
				taskManager.execute(task);
			}
			transaction.commit();
		}
		//
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doTearDown() throws Exception {
		// Nothing in this case
	}

}
