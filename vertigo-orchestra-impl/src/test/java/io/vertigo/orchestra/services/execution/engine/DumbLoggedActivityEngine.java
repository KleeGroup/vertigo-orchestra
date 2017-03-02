package io.vertigo.orchestra.services.execution.engine;

import io.vertigo.orchestra.impl.services.execution.AbstractActivityEngine;
import io.vertigo.orchestra.services.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbLoggedActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		getLogger().info("Info 1");
		getLogger().info("Info 2");
		workspace.setSuccess();
		try {
			Thread.sleep(1000 * 5);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
