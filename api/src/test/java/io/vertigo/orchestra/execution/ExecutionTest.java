package io.vertigo.orchestra.execution;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.dynamo.domain.model.DtList;
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
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.monitoring.MonitoringServices;
import io.vertigo.orchestra.planner.PlanificationState;
import io.vertigo.orchestra.planner.ProcessPlannerManager;
import io.vertigo.util.ListBuilder;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ExecutionTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private VTransactionManager transactionManager;
	@Inject
	private TaskManager taskManager;

	@Inject
	private ProcessPlannerManager processPlannerManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	@Inject
	private MonitoringServices monitoringServices;

	@Test
	public void clean() {
		// nothing
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void singleExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MANUEL")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// We plan right now
		processPlannerManager.plannProcessAt(proId, new Date());

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 12);

		final DtList<OProcessPlanification> processPlanifications = monitoringServices.getPlanificationsByProId(proId);
		// --- We check that planification is ok
		Assert.assertEquals(1, processPlanifications.size());
		final OProcessPlanification processPlanification = processPlanifications.get(0);
		Assert.assertEquals(PlanificationState.TRIGGERED.name(), processPlanification.getPstCd());
		// We check executions
		checkExecutions(proId, 0, 0, 1, 0);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void recurrentExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST RECURRENT")
				.withRecurrence()
				.withCron("*/15 * * * * ?")
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// Execution takes 10 secondes and it's schedule every 15 secondes
		Thread.sleep(1000 * 20);

		// --- We check the counts
		// After 30 secondes there is 1 execution done and 1 execution running
		checkExecutions(proId, 0, 1, 1, 0);

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

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// We plan right now
		processPlannerManager.plannProcessAt(proId, new Date());

		// Error is after 2 seconds
		Thread.sleep(1000 * 5);
		// --- We check the counts
		// After 5 secondes there is 1 process in error
		checkExecutions(proId, 0, 0, 0, 1);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void twoTask() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		// After 15 seconds the process is still running
		Thread.sleep(1000 * 15);
		checkExecutions(proId, 0, 1, 0, 0);
		// After 25 second the process is done
		Thread.sleep(1000 * 10);
		checkExecutions(proId, 0, 0, 1, 0);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void twoTasksWithError() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbErrorOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		// After 5 seconds the process is still running
		Thread.sleep(1000 * 5);
		checkExecutions(proId, 0, 1, 0, 0);
		// After 15 second the process is in Error
		Thread.sleep(1000 * 10);
		checkExecutions(proId, 0, 0, 0, 1);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testWithInitialParams() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testTwoTasksWithInitialParams() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS WITH PARAMS")
				.withManual()
				.withInitialParams("{\"filePath\" : \"toto/titi\"}")
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testException() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 TASKS")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbExceptionOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());

		// After 1 second the process is running
		Thread.sleep(1000 * 1);
		checkExecutions(proId, 0, 1, 0, 0);
		// After 5 seconds the process is in error because there is an exception after 3 seconds
		Thread.sleep(1000 * 4);
		checkExecutions(proId, 0, 0, 0, 1);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testMultiExecutionMisfire() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MULTI MISFIRE")
				.withManual()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());
		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testMultiExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MULTI")
				.withManual()
				.withMultiExecution()
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build();

		processDefinitionManager.createDefinition(processDefinition);

		final Long proId = processDefinition.getProcess().getProId();

		processPlannerManager.plannProcessAt(proId, new Date());
		processPlannerManager.plannProcessAt(proId, new Date());

		Thread.sleep(1000 * 60);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void recurrentExecutionWithMisfire() throws InterruptedException {

		processDefinitionManager.createDefinition(new ProcessDefinitionBuilder("TEST RECURRENT")
				.withRecurrence()
				.withCron("*/6 * * * * ?")
				.addTask("DUMB TASK", "io.vertigo.orchestra.execution.engine.DumbOTaskEngine", false)
				.build());

		Thread.sleep(1000 * 60);
	}

	private void checkExecutions(final Long proId, final int waitingCount, final int runningCount, final int doneCount, final int errorCount) {
		int waitingExecutionCount = 0;
		int runningExecutionCount = 0;
		int doneExecutionCount = 0;
		int errorExecutionCount = 0;

		for (final OProcessExecution processExecution : monitoringServices.getExecutionsByProId(proId)) {
			// --- We check the execution state of the process
			final DtList<OTaskExecution> taskExecutions = monitoringServices.getTaskExecutionsByPreId(processExecution.getPreId());
			int countTaskRunning = 0;
			int countTaskError = 0;
			for (final OTaskExecution taskExecution : taskExecutions) {
				switch (ExecutionState.valueOf(taskExecution.getEstCd())) {
					case WAITING:
						break;
					case RUNNING:
						countTaskRunning++;
						break;
					case ERROR:
						countTaskError++;
					default:
						break;
				}
			}
			switch (ExecutionState.valueOf(processExecution.getEstCd())) {
				case WAITING:
					waitingExecutionCount++;
					break;
				case RUNNING:
					runningExecutionCount++;
					// --- We check that there is one and only one task RUNNING if the process is Running
					Assert.assertEquals(1, countTaskRunning);
					break;
				case DONE:
					doneExecutionCount++;
					// --- We check that all tasks are done if a process is done
					for (final OTaskExecution taskExecution : taskExecutions) {
						Assert.assertEquals(ExecutionState.DONE.name(), taskExecution.getEstCd());
					}
					break;
				case ERROR:
					errorExecutionCount++;
					// --- We check that there is one and only one task is ERROR
					Assert.assertEquals(1, countTaskError);
					break;
				default:
					break;
			}
		}
		// --- We check the counts
		Assert.assertEquals(waitingCount, waitingExecutionCount);
		Assert.assertEquals(runningCount, runningExecutionCount);
		Assert.assertEquals(doneCount, doneExecutionCount);
		Assert.assertEquals(errorCount, errorExecutionCount);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doSetUp() throws Exception {
		//A chaque test on supprime tout
		try (VTransactionWritable transaction = transactionManager.createCurrentTransaction()) {
			final List<String> requests = new ListBuilder<String>()
					.add(" delete from o_execution_workspace;")
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
