package io.vertigo.orchestra.ui;

import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.impl.execution.AbstractActivityEngine;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class CallAlphaActivity extends AbstractActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {

		try {
			Thread.sleep(1000 * 2);
			// we make the call to Alpha
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		workspace.setPending();
		return workspace;
	}

}
