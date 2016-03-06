package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.TaskExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbErrorOTaskEngine implements OTaskEngine {

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final TaskExecutionWorkspace workspace) {
		workspace.setFailure();
		try {
			Thread.sleep(1000 * 2);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
