package io.vertigo.orchestra.process.execution;

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
