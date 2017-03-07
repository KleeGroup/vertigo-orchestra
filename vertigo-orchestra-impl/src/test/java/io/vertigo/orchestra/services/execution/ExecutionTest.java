package io.vertigo.orchestra.services.execution;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definitions.OrchestraDefinitionManager;
import io.vertigo.orchestra.definitions.ProcessDefinition;
import io.vertigo.orchestra.definitions.ProcessDefinitionBuilder;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.domain.execution.OActivityWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.services.OrchestraServices;
import io.vertigo.orchestra.services.schedule.SchedulerState;
import io.vertigo.orchestra.util.monitoring.MonitoringServices;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ExecutionTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private OrchestraDefinitionManager orchestraDefinitionManager;
	@Inject
	private OrchestraServices orchestraServices;

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

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MANUEL", "TEST MANUEL")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// We plan right now
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 12);

		final DtList<OProcessPlanification> processPlanifications = monitoringServices.getPlanificationsByProId(proId);
		// --- We check that planification is ok
		Assert.assertEquals(1, processPlanifications.size());
		final OProcessPlanification processPlanification = processPlanifications.get(0);
		Assert.assertEquals(SchedulerState.TRIGGERED.name(), processPlanification.getSstCd());
		// We check executions
		checkExecutions(proId, 0, 0, 1, 0);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void recurrentExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST SCHEDULED", "TEST SCHEDULED")
				.withCronExpression("*/15 * * * * ?")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		Thread.sleep(1000 * 1);
		// --- We get the first planification
		final DtList<OProcessPlanification> processPlanifications = monitoringServices.getPlanificationsByProId(proId);
		Assert.assertTrue(processPlanifications.size() >= 1);
		final OProcessPlanification processPlanification = processPlanifications.get(0);

		// We wait the planif
		Thread.sleep(processPlanification.getExpectedTime().getTime() - System.currentTimeMillis());

		// After 20 secondes there is 1 execution done and 1 execution running (for 5 secondes, half execution time)
		Thread.sleep(1000 * 20);
		// --- We check the counts
		checkExecutions(proId, 0, 1, 1, 0);

	}

	/**
	 * @throws InterruptedException
	 */
	//	@Test
	//	public void recurrentMultiNodeExecution() throws InterruptedException {
	//
	//		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST RECURRENT")
	//				.withRecurrence()
	//				.withMultiExecution()
	//				.withCron("*/4 * * * * ?")
	//				.addTask("DUMB ACTIVITY", "io.vertigo.orchestra.execution.engine.DumbActivityEngine", false)
	//				.build();
	//
	//		orchestraDefinitionManager.createDefinition(processDefinition);
	//
	//		Thread.sleep(1000 * 60 * 5);
	//
	//	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void executionError() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST ERROR", "TEST ERROR")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// We plan right now
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

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
	public void twoActivities() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 ACTIVITIES", "TEST 2 ACTIVITIES")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

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
	public void twoActivitiesWithError() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST 2 ACTIVITIES ERROR", "TEST 2 ACTIVITIES ERROR")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbErrorActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

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
		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST INITIAL PARAMS", "TEST INITIAL PARAMS")
				.addInitialParam("filePath", "toto/titi")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// We wait 5 secondes to be sure that execution is running
		Thread.sleep(1000 * 5);
		checkExecutions(proId, 0, 1, 0, 0); // We are sure that the process is running so we can continue the test safely

		final OActivityWorkspace activityWorkspace = monitoringServices
				.getActivityWorkspaceByAceId(monitoringServices.getActivityExecutionsByPreId(monitoringServices.getExecutionsByProId(proId).get(0).getPreId()).get(0).getAceId(), true);
		Assert.assertTrue(activityWorkspace.getWorkspace().contains("filePath"));

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testWithInitialParamsInPlanification() throws InterruptedException {
		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST INITIALPARAMS PLANIF", "TEST INITIALPARAMS PLANIF")
				.addInitialParam("filePath", "toto/titi")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		final Map<String, String> planifParams = new HashMap<>();
		planifParams.put("filePath", "tata/tutu");
		planifParams.put("planifParam", "titi");

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), planifParams);

		// We check 3 secondes to be sure that execution is running
		Thread.sleep(1000 * 3);
		checkExecutions(proId, 0, 1, 0, 0); // We are sure that the process is running so we can continue the test safely

		final OActivityWorkspace activityWorkspace = monitoringServices
				.getActivityWorkspaceByAceId(monitoringServices.getActivityExecutionsByPreId(monitoringServices.getExecutionsByProId(proId).get(0).getPreId()).get(0).getAceId(), true);
		Assert.assertTrue(activityWorkspace.getWorkspace().contains("tata/tutu"));
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testException() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST EXCEPTION", "TEST EXCEPTION")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbExceptionActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		//we schedule in 2 seconds
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(System.currentTimeMillis() + 1000 * 2), Collections.emptyMap());

		// After 4 second the process is running
		Thread.sleep(1000 * 4);
		checkExecutions(proId, 0, 1, 0, 0);
		// After 5 seconds the process is in error because there is an exception after 3 seconds
		Thread.sleep(1000 * 5);
		checkExecutions(proId, 0, 0, 0, 1);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testMonoExecutionMisfire() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MULTI MISFIRE", "TEST MULTI MISFIRE")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// We wait 3 seconds
		Thread.sleep(1000 * 3);
		// We should have 1 planification triggered and 1 misfired
		checkPlanifications(proId, 0, 1, 1);
		// We should have one execution running
		checkExecutions(proId, 0, 1, 0, 0);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testLog() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST LOG", "TEST LOG")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbLoggedActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// We wait 10 seconds until it's finished
		Thread.sleep(1000 * 10);

		checkExecutions(proId, 0, 0, 1, 0); // We are sure that the process is done so we can continue the test safely

		final Optional<OActivityLog> activityLog = monitoringServices
				.getActivityLogByAceId(monitoringServices.getActivityExecutionsByPreId(monitoringServices.getExecutionsByProId(proId).get(0).getPreId()).get(0).getAceId());
		Assert.assertTrue(activityLog.isPresent());

	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testMultiExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MULTI", "TEST MULTI")
				.withMultiExecution()
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());
		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// We wait 3 seconds
		Thread.sleep(1000 * 5);
		// We should have 2 planifications triggered
		checkPlanifications(proId, 0, 2, 0);
		// We should have two executions running
		checkExecutions(proId, 0, 2, 0, 0);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test
	public void testFinishedExecution() throws InterruptedException {

		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST MULTI", "TEST MULTI")
				.withMultiExecution()
				.addActivity("DUMB ACTIVITY FINISHED", "DUMB ACTIVITY FINISHED", io.vertigo.orchestra.services.execution.engine.DumbFinishedActivityEngine.class)
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.services.execution.engine.DumbActivityEngine.class)
				.build();

		orchestraDefinitionManager.createOrUpdateDefinition(processDefinition);

		final Long proId = processDefinition.getId();

		orchestraServices.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// We wait 10 seconds
		Thread.sleep(1000 * 10);
		// we have one process_execution done
		checkExecutions(proId, 0, 0, 1, 0);
		// we have one and only one activity_execution done
		checkActivityExecutions(proId, 0, 0, 1, 0);

	}

	private void checkPlanifications(final Long proId, final int waitingCount, final int triggeredCount, final int misfiredCount) {
		int waitingPlanificationCount = 0;
		int triggeredPlanificationCount = 0;
		int misfiredPlanificationCount = 0;

		for (final OProcessPlanification processPlanification : monitoringServices.getPlanificationsByProId(proId)) {

			switch (SchedulerState.valueOf(processPlanification.getSstCd())) {
				case WAITING:
					waitingPlanificationCount++;
					break;
				case TRIGGERED:
					triggeredPlanificationCount++;
					break;
				case MISFIRED:
					misfiredPlanificationCount++;
					break;
				case RESCUED:
				default:
					break;
			}
		}
		// --- We check the counts
		Assert.assertEquals(waitingCount, waitingPlanificationCount);
		Assert.assertEquals(triggeredCount, triggeredPlanificationCount);
		Assert.assertEquals(misfiredCount, misfiredPlanificationCount);
	}

	private void checkExecutions(final Long proId, final int waitingCount, final int runningCount, final int doneCount, final int errorCount) {
		int waitingExecutionCount = 0;
		int runningExecutionCount = 0;
		int doneExecutionCount = 0;
		int errorExecutionCount = 0;

		for (final OProcessExecution processExecution : monitoringServices.getExecutionsByProId(proId)) {
			// --- We check the execution state of the process
			final DtList<OActivityExecution> activityExecutions = monitoringServices.getActivityExecutionsByPreId(processExecution.getPreId());
			int countActivitiesRunning = 0;
			int countActivitiesError = 0;
			for (final OActivityExecution activityExecution : activityExecutions) {
				switch (ExecutionState.valueOf(activityExecution.getEstCd())) {
					case WAITING:
						break;
					case RUNNING:
						countActivitiesRunning++;
						break;
					case DONE:
						break;
					case ERROR:
						countActivitiesError++;
						break;
					case SUBMITTED:
					case PENDING:
					default:
						throw new UnsupportedOperationException("Unsupported state :" + activityExecution.getEstCd());
				}
			}
			switch (ExecutionState.valueOf(processExecution.getEstCd())) {
				case WAITING:
					waitingExecutionCount++;
					break;
				case RUNNING:
					runningExecutionCount++;
					// --- We check that there is one and only one activity RUNNING if the process is Running
					Assert.assertEquals(1, countActivitiesRunning);
					break;
				case DONE:
					doneExecutionCount++;
					// --- We check that all activities are done if a process is done
					for (final OActivityExecution activityExecution : activityExecutions) {
						Assert.assertEquals(ExecutionState.DONE.name(), activityExecution.getEstCd());
					}
					break;
				case ERROR:
					errorExecutionCount++;
					// --- We check that there is one and only one activity is ERROR
					Assert.assertEquals(1, countActivitiesError);
					break;
				case SUBMITTED:
				case PENDING:
				default:
					throw new UnsupportedOperationException("Unsupported state :" + processExecution.getEstCd());
			}
		}
		// --- We check the counts
		Assert.assertEquals("waiting ", waitingCount, waitingExecutionCount);
		Assert.assertEquals("running", runningCount, runningExecutionCount);
		Assert.assertEquals("done", doneCount, doneExecutionCount);
		Assert.assertEquals("error", errorCount, errorExecutionCount);
	}

	private void checkActivityExecutions(final Long proId, final int waitingCount, final int runningCount, final int doneCount, final int errorCount) {

		for (final OProcessExecution processExecution : monitoringServices.getExecutionsByProId(proId)) {
			// --- We check the execution state of the process
			final DtList<OActivityExecution> activityExecutions = monitoringServices.getActivityExecutionsByPreId(processExecution.getPreId());
			int waitingExecutionCount = 0;
			int runningExecutionCount = 0;
			int doneExecutionCount = 0;
			int errorExecutionCount = 0;
			for (final OActivityExecution activityExecution : activityExecutions) {
				switch (ExecutionState.valueOf(activityExecution.getEstCd())) {
					case WAITING:
						waitingExecutionCount++;
						break;
					case RUNNING:
						runningExecutionCount++;
						break;
					case DONE:
						doneExecutionCount++;
						break;
					case ERROR:
						errorExecutionCount++;
						break;
					case SUBMITTED:
					case PENDING:
					default:
						throw new UnsupportedOperationException("Unsupported state :" + activityExecution.getEstCd());
				}
			}
			// --- We check the counts
			Assert.assertEquals("waiting ", waitingCount, waitingExecutionCount);
			Assert.assertEquals("running", runningCount, runningExecutionCount);
			Assert.assertEquals("done", doneCount, doneExecutionCount);
			Assert.assertEquals("error", errorCount, errorExecutionCount);

		}

	}

}
