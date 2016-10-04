package io.vertigo.orchestra.execution.process;

import java.io.Serializable;

public final class ProcessExecution implements Serializable {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long preId;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private Integer executionTime;
	private String status;
	private Boolean checked;
	private java.util.Date checkingDate;
	private String checkingComment;
	private Boolean hasLogFile;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(final Long preId) {
		this.preId = preId;
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

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(final Boolean checked) {
		this.checked = checked;
	}

	public java.util.Date getCheckingDate() {
		return checkingDate;
	}

	public void setCheckingDate(final java.util.Date checkingDate) {
		this.checkingDate = checkingDate;
	}

	public String getCheckingComment() {
		return checkingComment;
	}

	public void setCheckingComment(final String checkingComment) {
		this.checkingComment = checkingComment;
	}

	public Boolean getHasLogFile() {
		return hasLogFile;
	}

	public void setHasLogFile(final Boolean hasLogFile) {
		this.hasLogFile = hasLogFile;
	}

}
