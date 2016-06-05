package io.vertigo.orchestra.execution;

/**
 * Enumération des états d'execution.
 * @author mlaroche.
 * @version $Id$
 */
public enum ExecutionState {

	WAITING,

	//RESERVED,

	SUBMITTED,

	RUNNING,

	DONE,

	ERROR,

	//CANCELED,

	//ABORTED,

	PENDING;

}
