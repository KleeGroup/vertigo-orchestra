package io.vertigo.orchestra.execution;

import java.util.Collections;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Assert;

import io.vertigo.orchestra.AbstractOrchestraTestCaseJU4;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionBuilder;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.process.ProcessManager;
import io.vertigo.orchestra.process.report.ActivityExecution;
import io.vertigo.orchestra.process.report.ExecutionSummary;
import io.vertigo.util.DateUtil;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ReportingTest extends AbstractOrchestraTestCaseJU4 {

	@Inject
	private ProcessManager processManager;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;
	@Inject
	private ProcessManager processExecutionManager;

	private ProcessDefinition executeProcess() throws InterruptedException {
		final ProcessDefinition processDefinition = new ProcessDefinitionBuilder("TEST_SINGLE", "TEST_SINGLE")
				.addActivity("DUMB ACTIVITY", "DUMB ACTIVITY", io.vertigo.orchestra.execution.engine.DumbActivityEngine.class)
				.build();

		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);

		final Long proId = processDefinition.getId();
		// We check the save is ok
		Assert.assertNotNull(proId);

		// We plan right now
		processManager.getScheduler()
				.scheduleAt(processDefinition, new Date(), Collections.emptyMap());

		// The task takes 10 secondes to run we wait 12 secondes to check the final states
		Thread.sleep(1000 * 12);
		return processDefinition;
	}

	/**
	 * @throws InterruptedException
	 */
	//@Test
	public void testReport() throws InterruptedException {

		final ProcessDefinition processDefinition = executeProcess();
		// -1h +1h
		final ExecutionSummary executionSummary = processExecutionManager.getReport().getSummaryByDateAndName(processDefinition, new Date(DateUtil.newDateTime().getTime() - 60 * 60 * 1000),
				new Date(DateUtil.newDateTime().getTime() + 60 * 60 * 1000));
		Assert.assertTrue(1 == executionSummary.getSuccessfulCount());
	}

	/**
	 * @throws InterruptedException
	 */
	//@Test
	public void testLog() throws InterruptedException {
		final ProcessDefinition processDefinition = executeProcess();
		// ---
		final ActivityExecution activityExecution = processExecutionManager.getReport()
				.getActivityExecutionsByProcessExecution(processExecutionManager.getReport().getProcessExecutions(processDefinition, "", 10, 0).get(0).getPreId()).get(0);
		Assert.assertTrue(processExecutionManager.getLogger().getTechnicalLogFileForActivity(activityExecution.getAceId()).isPresent());
	}

}
