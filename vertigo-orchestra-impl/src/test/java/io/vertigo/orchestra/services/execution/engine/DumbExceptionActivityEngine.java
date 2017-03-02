package io.vertigo.orchestra.services.execution.engine;

import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbExceptionActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setValue("currentObject", "An object");
		try {
			Thread.sleep(4 * 1000);
		} catch (final InterruptedException e) {
			// we do nothing
		}
		Integer.valueOf("a");
		return workspace;
	}

}
