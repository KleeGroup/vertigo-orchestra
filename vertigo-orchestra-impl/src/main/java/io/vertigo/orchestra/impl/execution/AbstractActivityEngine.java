package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.execution.activity.ActivityEngine;
import io.vertigo.orchestra.execution.activity.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.activity.ActivityLogger;

/**
 * Activity engine abstrait offrant des services communs (logger)
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

	/**
	 * Place une activité en attente
	 * @param workspace le worspace de l'activité
	 * @return le workspace modifié
	 */
	public ActivityExecutionWorkspace setActivityPending(final ActivityExecutionWorkspace workspace) {
		workspace.setPending();
		processExecutionManager.setActivityExecutionPending(workspace.getActivityExecutionId(), workspace);
		return workspace;
	}

}
