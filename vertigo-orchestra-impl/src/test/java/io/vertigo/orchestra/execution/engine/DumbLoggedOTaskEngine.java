package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.impl.execution.AbstractOTaskEngine;
import io.vertigo.orchestra.impl.execution.TaskExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbLoggedOTaskEngine extends AbstractOTaskEngine {

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final TaskExecutionWorkspace workspace) {
		getLogger().info("Info 1");
		getLogger().info("Info 2");
		workspace.setSucess();
		try {
			Thread.sleep(1000 * 5);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return workspace;
	}

}
