package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ActivityLogger;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public abstract class AbstractActivityEngine implements ActivityEngine {

	private final ActivityLogger activityLogger = new ActivityLogger(getClass().getName());

	@Inject
	private ProcessExecutionManager processExecutionManager;

	/**
	 * Getter for the logger
	 * @return.
	 */
	public ActivityLogger getLogger() {
		return activityLogger;
	}

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace successfulPostTreatment(final ActivityExecutionWorkspace workspace) {
		//nothing
		return workspace;
	}

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace errorPostTreatment(final ActivityExecutionWorkspace workspace, final Exception e) {
		//nothing
		return workspace;
	}

	public ActivityExecutionWorkspace setActivityPending(final ActivityExecutionWorkspace workspace) {
		processExecutionManager.setActivityExecutionPending(workspace.getActivityExecutionId());
		workspace.setPending();
		return workspace;
	}

}
