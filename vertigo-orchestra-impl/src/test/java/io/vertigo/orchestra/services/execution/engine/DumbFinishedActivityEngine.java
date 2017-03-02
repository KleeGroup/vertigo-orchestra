package io.vertigo.orchestra.services.execution.engine;

import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbFinishedActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setFinished();
		try {
			Thread.sleep(1000 * 1);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
