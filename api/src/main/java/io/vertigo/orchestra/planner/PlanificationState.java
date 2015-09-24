package io.vertigo.orchestra.planner;

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
