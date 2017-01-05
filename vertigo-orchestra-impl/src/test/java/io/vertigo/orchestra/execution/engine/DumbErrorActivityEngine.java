package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.impl.process.execution.AbstractActivityEngine;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbErrorActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setFailure();
		try {
			Thread.sleep(1000 * 2);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
