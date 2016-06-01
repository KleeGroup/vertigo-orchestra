package io.vertigo.orchestra.ui;

import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.impl.execution.AbstractActivityEngine;

/**
 * TODO : Description de la classe.
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
