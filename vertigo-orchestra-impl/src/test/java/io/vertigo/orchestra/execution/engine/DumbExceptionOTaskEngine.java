package io.vertigo.orchestra.execution.engine;

import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.TaskExecutionWorkspace;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class DumbExceptionOTaskEngine implements OTaskEngine {

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final TaskExecutionWorkspace workspace) {
		workspace.setValue("currentObject", "An object");
		try {
			Thread.sleep(3 * 1000);
		} catch (final InterruptedException e) {
			// we do nothing
		}
		Integer.valueOf("a");
		return workspace;
	}

}
