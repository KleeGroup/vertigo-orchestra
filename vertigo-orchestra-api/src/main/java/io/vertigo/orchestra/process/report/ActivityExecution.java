package io.vertigo.orchestra.process.report;

import java.io.Serializable;
import java.util.Date;

import io.vertigo.lang.Assertion;

/**
 * Representation of an activityExecution
 * @author mlaroche
 *
 */
public final class ActivityExecution implements Serializable {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final Long aceId;
	private final String label;
	private final Date beginTime;
	private final Date endTime;
	private final Integer executionTime;
	private final String status;
	private final String workspaceIn;
	private final String workspaceOut;
	private final Boolean hasLogFile;
	private final Boolean hasTechnicalLog;

	/**
	 * Constructor.
	 * @param aceId if of activity execution
	 * @param label label of activity
	 * @param beginTime begin tume
	 * @param endTime end time
	 * @param executionTime execution time in seconds
	 * @param status status of activity
	 * @param workspaceIn the workspace as input parameter
	 * @param workspaceOut the workspace as output parameter
	 * @param hasLogFile if the activity has a logfile
	 * @param hasTechnicalLog of the activity has a technical logfile
	 */
	public ActivityExecution(
			final Long aceId,
			final String label,
			final Date beginTime,
			final Date endTime,
			final Integer executionTime,
			final String status,
			final String workspaceIn,
			final String workspaceOut,
			final Boolean hasLogFile,
			final Boolean hasTechnicalLog) {
		Assertion.checkNotNull(aceId);
		// ---
		this.aceId = aceId;
		this.label = label;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.executionTime = executionTime;
		this.status = status;
		this.workspaceIn = workspaceIn;
		this.workspaceOut = workspaceOut;
		this.hasLogFile = hasLogFile;
		this.hasTechnicalLog = hasTechnicalLog;
	}

	public Long getAceId() {
		return aceId;
	}

	public String getLabel() {
		return label;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

	public String getStatus() {
		return status;
	}

	public String getWorkspaceIn() {
		return workspaceIn;
	}

	public String getWorkspaceOut() {
		return workspaceOut;
	}

	public Boolean getHasLogFile() {
		return hasLogFile;
	}

	public Boolean getHasTechnicalLog() {
		return hasTechnicalLog;
	}

}
