package io.vertigo.orchestra.execution;

import io.vertigo.lang.Manager;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessExecutionManager extends Manager {
	// Nothing

	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);
}
