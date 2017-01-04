package io.vertigo.orchestra.scheduler;

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
