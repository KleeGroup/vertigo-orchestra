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
		try {
			Thread.sleep(1000 * 10);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
