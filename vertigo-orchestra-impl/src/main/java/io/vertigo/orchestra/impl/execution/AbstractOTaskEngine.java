package io.vertigo.orchestra.impl.execution;

import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.TaskLogger;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public abstract class AbstractOTaskEngine implements OTaskEngine {

	private final TaskLogger taskLogger = new TaskLogger(getClass().getName());

	/**
	 * Getter for the logger
	 * @return.
	 */
	public TaskLogger getLogger() {
		return taskLogger;
	}

}
