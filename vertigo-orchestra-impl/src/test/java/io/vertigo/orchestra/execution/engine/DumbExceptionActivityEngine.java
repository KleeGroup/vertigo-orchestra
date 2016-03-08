package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbExceptionActivityEngine implements ActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setValue("currentObject", "An object");
		try {
			Thread.sleep(3 * 1000);
		} catch (final InterruptedException e) {
			// we do nothing
		}
		Integer.valueOf("a");
		return workspace;
	}

}
