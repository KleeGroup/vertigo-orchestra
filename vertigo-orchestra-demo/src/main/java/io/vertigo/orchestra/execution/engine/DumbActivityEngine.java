package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setSuccess();
		try {
			Thread.sleep(1000 * 10L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
