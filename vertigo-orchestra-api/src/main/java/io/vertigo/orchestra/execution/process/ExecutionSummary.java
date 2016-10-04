package io.vertigo.orchestra.execution.process;

import java.io.Serializable;

public final class ExecutionSummary implements Serializable {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long proId;
	private String processName;
	private String processLabel;
	private java.util.Date lastExecutionTime;
	private java.util.Date nextExecutionTime;
	private Integer errorsCount;
	private Integer misfiredCount;
	private Integer successfulCount;
	private Integer runningCount;
	private Integer averageExecutionTime;
	private String health;

	public Long getProId() {
		return proId;
	}

	public void setProId(final Long proId) {
		this.proId = proId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(final String processName) {
		this.processName = processName;
	}

	public String getProcessLabel() {
		return processLabel;
	}

	public void setProcessLabel(final String processLabel) {
		this.processLabel = processLabel;
	}

	public java.util.Date getLastExecutionTime() {
		return lastExecutionTime;
	}

	public void setLastExecutionTime(final java.util.Date lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}

	public java.util.Date getNextExecutionTime() {
		return nextExecutionTime;
	}

	public void setNextExecutionTime(final java.util.Date nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}

	public Integer getErrorsCount() {
		return errorsCount;
	}

	public void setErrorsCount(final Integer errorsCount) {
		this.errorsCount = errorsCount;
	}

	public Integer getMisfiredCount() {
		return misfiredCount;
	}

	public void setMisfiredCount(final Integer misfiredCount) {
		this.misfiredCount = misfiredCount;
	}

	public Integer getSuccessfulCount() {
		return successfulCount;
	}

	public void setSuccessfulCount(final Integer successfulCount) {
		this.successfulCount = successfulCount;
	}

	public Integer getRunningCount() {
		return runningCount;
	}

	public void setRunningCount(final Integer runningCount) {
		this.runningCount = runningCount;
	}

	public Integer getAverageExecutionTime() {
		return averageExecutionTime;
	}

	public void setAverageExecutionTime(final Integer averageExecutionTime) {
		this.averageExecutionTime = averageExecutionTime;
	}

	public String getHealth() {
		return health;
	}

	public void setHealth(final String health) {
		this.health = health;
	}
}
