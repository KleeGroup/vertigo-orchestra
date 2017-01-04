package io.vertigo.orchestra.impl.execution;

import java.util.Date;
import java.util.List;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.execution.activity.ActivityExecution;
import io.vertigo.orchestra.execution.process.ExecutionSummary;
import io.vertigo.orchestra.execution.process.ProcessExecution;

/**
 * Interface de plugin de supervision des processus.
 * @author mlaroche
 *
 */
public interface ProcessReportPlugin extends Plugin {

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getProcessExecutions(ProcessDefinition, String, Integer, Integer)
	 */
	List<ProcessExecution> getProcessExecutions(ProcessDefinition processDefinition, String status, Integer limit, Integer offset);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getSummariesByDate(Date, Date, String)
	 */
	List<ExecutionSummary> getSummariesByDate(Date minDate, Date maxDate, String status);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getSummaryByDateAndName(ProcessDefinition, Date, Date)
	 */
	ExecutionSummary getSummaryByDateAndName(ProcessDefinition processDefinition, Date minDate, Date maxDate);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getProcessExecution(Long)
	 */
	ProcessExecution getProcessExecution(Long preId);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getActivityExecutionsByProcessExecution(Long)
	 */
	List<ActivityExecution> getActivityExecutionsByProcessExecution(Long preId);

	/**
	 * @see io.vertigo.orchestra.execution.ProcessExecutionManager#getActivityExecution(Long)
	 */
	ActivityExecution getActivityExecution(Long aceId);

}
