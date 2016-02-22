package io.vertigo.orchestra.scheduler;

/**
 * Enumération des états de planification.
 * @author mlaroche.
 * @version $Id$
 */
public enum PlanificationState {
	WAITING,

	RESERVED,

	TRIGGERED,

	MISFIRED,

	CANCELED;
}
