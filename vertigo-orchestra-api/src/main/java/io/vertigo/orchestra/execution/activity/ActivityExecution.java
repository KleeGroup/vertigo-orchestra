package io.vertigo.orchestra.execution.activity;

import java.io.Serializable;

public final class ActivityExecution implements Serializable {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long aceId;
	private String label;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private Integer executionTime;
	private String status;
	private String workspaceIn;
	private String workspaceOut;
	private Boolean hasLogFile;
	private Boolean hasTechnicalLog;

	public Long getAceId() {
		return aceId;
	}

	public void setAceId(final Long aceId) {
		this.aceId = aceId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}

	public java.util.Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(final java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(final java.util.Date endTime) {
		this.endTime = endTime;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(final Integer executionTime) {
		this.executionTime = executionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getWorkspaceIn() {
		return workspaceIn;
	}

	public void setWorkspaceIn(final String workspaceIn) {
		this.workspaceIn = workspaceIn;
	}

	public String getWorkspaceOut() {
		return workspaceOut;
	}

	public void setWorkspaceOut(final String workspaceOut) {
		this.workspaceOut = workspaceOut;
	}

	public Boolean getHasLogFile() {
		return hasLogFile;
	}

	public void setHasLogFile(final Boolean hasLogFile) {
		this.hasLogFile = hasLogFile;
	}

	public Boolean getHasTechnicalLog() {
		return hasTechnicalLog;
	}

	public void setHasTechnicalLog(final Boolean hasTechnicalLog) {
		this.hasTechnicalLog = hasTechnicalLog;
	}

}