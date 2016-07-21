package io.vertigo.orchestra.scheduler;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.vertigo.lang.Manager;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessSchedulerManager extends Manager {

	void scheduleAt(final Long proId, final Date planifiedTime, final Optional<String> initialParams);

	List<Long> getProcessToExecute();

	void triggerPlanification(final Long prpId);

	void misfirePlanification(final Long prpId);

	void resetFuturePlanificationOfProcess(final String processName);

}
