package io.vertigo.orchestra.ui;

import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class EmptyActivityEngine implements ActivityEngine {

	/** {@inheritDoc} */
	@Override
	public ActivityExecutionWorkspace execute(final ActivityExecutionWorkspace workspace) {
		workspace.setSucess();
		try {
			Thread.sleep(1000 * 10);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
