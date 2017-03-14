package io.vertigo.orchestra.services.execution;

/**
 * Enumération des états d'execution.
 * @author mlaroche.
 * @version $Id$
 */
public enum ExecutionState {

	WAITING,

	SUBMITTED,

	RUNNING,

	DONE,

	ERROR,

	PENDING;

}
