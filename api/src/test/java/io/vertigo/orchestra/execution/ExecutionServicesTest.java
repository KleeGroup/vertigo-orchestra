package io.vertigo.orchestra.execution;

import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.metamodel.TaskDefinitionBuilder;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.transaction.VTransactionManager;
import io.vertigo.dynamo.transaction.VTransactionWritable;
import io.vertigo.dynamox.task.TaskEngineProc;
import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.util.ListBuilder;

import java.util.Date;
import java.util.List;

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
	private VTransactionManager transactionManager;
	@Inject
	private TaskManager taskManager;

	@Inject
	private ProcessPlannerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Test
	public void clean() {
		// nothing
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void dumbexecution() throws InterruptedException {

		final ProcessDefinition processDefinitionWrapper = new ProcessDefinitionBuilder("TEST MANUEL")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.registerDefinition(processDefinitionWrapper);

		final Long proId = processDefinitionWrapper.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());
		Thread.sleep(1000 * 60);

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void dumbRecurrentExecution() throws InterruptedException {

		processDefinitionManager.registerDefinition(new ProcessDefinitionBuilder("TEST RECURRENT")
				.withRecurrence()
				.withDelay(100L)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void executionError() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST ERROR")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbErrorOTaskEngine", false)
				.build();

		processDefinitionManager.registerDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());
		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testTwoTasks() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.registerDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testTwoTasksWithError() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbErrorOTaskEngine", false)
				.build();

		processDefinitionManager.registerDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		//A chaque test on supprime tout
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
