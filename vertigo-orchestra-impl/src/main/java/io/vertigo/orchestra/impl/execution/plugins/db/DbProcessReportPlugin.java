package io.vertigo.orchestra.impl.execution.plugins.db;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.execution.activity.ActivityExecution;
import io.vertigo.orchestra.execution.process.ExecutionSummary;
import io.vertigo.orchestra.execution.process.ProcessExecution;
import io.vertigo.orchestra.impl.execution.plugins.ProcessReportPlugin;
import io.vertigo.orchestra.monitoring.dao.summary.SummaryPAO;
import io.vertigo.orchestra.monitoring.dao.uiexecutions.UiexecutionsPAO;
import io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OActivityExecutionUi;
import io.vertigo.orchestra.monitoring.domain.uiexecutions.OProcessExecutionUi;

/**
 * Récupération des reporting d'execution en BDD.
 * @author mlaroche
 *
 */
@Transactional
public class DbProcessReportPlugin implements ProcessReportPlugin {

	@Inject
	private UiexecutionsPAO uiexecutionsPAO;
	@Inject
	private SummaryPAO summaryPAO;

	@Override
	public List<ProcessExecution> getProcessExecutions(final ProcessDefinition processDefinition, final String status, final Integer limit, final Integer offset) {
		return decodeExecutionList(uiexecutionsPAO.getExecutionsByProcessName(processDefinition.getName(), status, limit, offset));
	}

	@Override
	public List<ExecutionSummary> getSummariesByDate(final Date minDate, final Date maxDate, final String status) {
		return decodeSummaryList(summaryPAO.getExecutionSummariesByDate(minDate, maxDate, status));
	}

	@Override
	public ExecutionSummary getSummaryByDateAndName(final ProcessDefinition processDefinition, final Date minDate, final Date maxDate) {
		return decodeSummary(summaryPAO.getExecutionSummaryByDateAndName(minDate, maxDate, processDefinition.getName()));
	}

	@Override
	public ProcessExecution getProcessExecution(final Long preId) {
		return decodeExecution(uiexecutionsPAO.getExecutionByPreId(preId));
	}

	@Override
	public List<ActivityExecution> getActivityExecutionsByProcessExecution(final Long preId) {
		return decodeActivityExecutionList(uiexecutionsPAO.getActivitiesByPreId(preId));
	}

	@Override
	public ActivityExecution getActivityExecution(final Long aceId) {
		return decodeActivityExecution(uiexecutionsPAO.getActivitiyByAceId(aceId));
	}

	// Utilitaire de transformation//
	private static ExecutionSummary decodeSummary(final OExecutionSummary summary) {
		final ExecutionSummary executionSummary = new ExecutionSummary();
		executionSummary.setProId(summary.getProId());
		executionSummary.setProcessName(summary.getProcessName());
		executionSummary.setProcessLabel(summary.getProcessLabel());
		executionSummary.setLastExecutionTime(summary.getLastExecutionTime());
		executionSummary.setNextExecutionTime(summary.getNextExecutionTime());
		executionSummary.setErrorsCount(summary.getErrorsCount());
		executionSummary.setMisfiredCount(summary.getMisfiredCount());
		executionSummary.setSuccessfulCount(summary.getSuccessfulCount());
		executionSummary.setRunningCount(summary.getRunningCount());
		executionSummary.setAverageExecutionTime(summary.getAverageExecutionTime());
		executionSummary.setHealth(summary.getHealth());

		return executionSummary;
	}

	private static List<ExecutionSummary> decodeSummaryList(final List<OExecutionSummary> summaries) {
		return summaries
				.stream()
				.map(summary -> decodeSummary(summary))
				.collect(Collectors.toList());

	}

	private static ProcessExecution decodeExecution(final OProcessExecutionUi execution) {
		final ProcessExecution processExecution = new ProcessExecution();
		processExecution.setPreId(execution.getPreId());
		processExecution.setBeginTime(execution.getBeginTime());
		processExecution.setEndTime(execution.getEndTime());
		processExecution.setExecutionTime(execution.getExecutionTime());
		processExecution.setStatus(execution.getStatus());
		processExecution.setChecked(execution.getChecked());
		processExecution.setCheckingDate(execution.getCheckingDate());
		processExecution.setCheckingComment(execution.getCheckingComment());
		processExecution.setHasLogFile(execution.getHasLogFile());
		return processExecution;
	}

	private static List<ProcessExecution> decodeExecutionList(final List<OProcessExecutionUi> executions) {
		return executions
				.stream()
				.map(execution -> decodeExecution(execution))
				.collect(Collectors.toList());

	}

	private static ActivityExecution decodeActivityExecution(final OActivityExecutionUi execution) {
		final ActivityExecution activityExecution = new ActivityExecution();
		activityExecution.setAceId(execution.getAceId());
		activityExecution.setLabel(execution.getLabel());
		activityExecution.setBeginTime(execution.getBeginTime());
		activityExecution.setEndTime(execution.getEndTime());
		activityExecution.setExecutionTime(execution.getExecutionTime());
		activityExecution.setStatus(execution.getStatus());
		activityExecution.setWorkspaceIn(execution.getWorkspaceIn());
		activityExecution.setWorkspaceOut(execution.getWorkspaceOut());
		activityExecution.setHasLogFile(execution.getHasLogFile());
		activityExecution.setHasTechnicalLog(execution.getHasTechnicalLog());
		return activityExecution;
	}

	private static List<ActivityExecution> decodeActivityExecutionList(final List<OActivityExecutionUi> executions) {
		return executions
				.stream()
				.map(execution -> decodeActivityExecution(execution))
				.collect(Collectors.toList());

	}

}
