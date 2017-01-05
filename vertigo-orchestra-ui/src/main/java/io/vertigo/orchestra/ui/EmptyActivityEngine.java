package io.vertigo.orchestra.ui;

import io.vertigo.orchestra.impl.process.execution.AbstractActivityEngine;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;

/**
 * Activité basique (Démo).
 *
 * @author mlaroche.
 * @version $Id$
 */
public class EmptyActivityEngine extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setSuccess();
		workspace.setLogFile("/test/fichiertest.log");
		try {
			Thread.sleep(1 * 1000L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
