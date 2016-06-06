package io.vertigo.orchestra.scheduler;

/**
 * Enumération des états de planification.
 * @author mlaroche.
 * @version $Id$
 */
public enum PlanificationState {
	WAITING,

	TRIGGERED,

	MISFIRED,

	CANCELED,

	RESCUED;

	//Todo : ?????
	//RESERVED,
}
