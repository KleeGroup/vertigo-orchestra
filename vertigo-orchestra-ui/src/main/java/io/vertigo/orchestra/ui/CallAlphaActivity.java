package io.vertigo.orchestra.ui;

import io.vertigo.orchestra.impl.process.execution.AbstractActivityEngine;
import io.vertigo.orchestra.process.execution.ActivityExecutionWorkspace;

/**
 * Activité basique (Démo).
 *
 * @author mlaroche.
 * @version $Id$
 */
public class CallAlphaActivity extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {

		try {
			Thread.sleep(2 * 1000L);
			// we make the call to Alpha
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		workspace.setPending();
		return workspace;
	}

}
