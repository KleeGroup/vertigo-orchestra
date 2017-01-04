package io.vertigo.orchestra.execution.process;

import java.io.Serializable;
import java.util.Date;

import io.vertigo.lang.Assertion;

public final class ExecutionSummary implements Serializable {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final Long proId;
	private final String processName;
	private final String processLabel;
	private final java.util.Date lastExecutionTime;
	private final java.util.Date nextExecutionTime;
	private final Integer errorsCount;
	private final Integer misfiredCount;
	private final Integer successfulCount;
	private final Integer runningCount;
	private final Integer averageExecutionTime;
	private final String health;

	/**
	 * Constructor.
	 * @param proId	id of process
	 * @param processName name of process
	 * @param processLabel label of process
	 * @param lastExecutionTime time of last execution
	 * @param nextExecutionTime time of next planification
	 * @param errorsCount number of errors
	 * @param misfiredCount number of misfired planifications
	 * @param successfulCount number of success
	 * @param runningCount number of running
	 * @param averageExecutionTime average execution time in seconds
	 * @param health health status of the process
	 */
	public ExecutionSummary(final Long proId, final String processName, final String processLabel, final Date lastExecutionTime, final Date nextExecutionTime, final Integer errorsCount,
			final Integer misfiredCount, final Integer successfulCount,
			final Integer runningCount, final Integer averageExecutionTime, final String health) {
		this.proId = proId;
		Assertion.checkNotNull(proId);
		Assertion.checkNotNull(processName);
		// ---
		this.processName = processName;
		this.processLabel = processLabel;
		this.lastExecutionTime = lastExecutionTime;
		this.nextExecutionTime = nextExecutionTime;
		this.errorsCount = errorsCount;
		this.misfiredCount = misfiredCount;
		this.successfulCount = successfulCount;
		this.runningCount = runningCount;
		this.averageExecutionTime = averageExecutionTime;
		this.health = health;
	}

	public Long getProId() {
		return proId;
	}

	public String getProcessName() {
		return processName;
	}

	public String getProcessLabel() {
		return processLabel;
	}

	public java.util.Date getLastExecutionTime() {
		return lastExecutionTime;
	}

	public java.util.Date getNextExecutionTime() {
		return nextExecutionTime;
	}

	public Integer getErrorsCount() {
		return errorsCount;
	}

	public Integer getMisfiredCount() {
		return misfiredCount;
	}

	public Integer getSuccessfulCount() {
		return successfulCount;
	}

	public Integer getRunningCount() {
		return runningCount;
	}

	public Integer getAverageExecutionTime() {
		return averageExecutionTime;
	}

	public String getHealth() {
		return health;
	}
}
