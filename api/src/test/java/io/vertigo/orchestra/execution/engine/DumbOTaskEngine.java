package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.impl.execution.TaskExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbOTaskEngine implements OTaskEngine {

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final TaskExecutionWorkspace workspace) {
		workspace.setSucess();
		try {
			Thread.sleep(1000 * 10);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
