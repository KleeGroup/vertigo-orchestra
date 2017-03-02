package io.vertigo.orchestra.services.schedule;

/**
 * Enumération des états de planification.
 *
 * ==>WAITING ==> TRIGGERED
 * 			  ==>MISFIRE (If
 *
 * @author mlaroche.
 * @version $Id$
 */
public enum SchedulerState {
	WAITING,

	TRIGGERED,

	MISFIRED,

	RESCUED;
}
