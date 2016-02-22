package io.vertigo.orchestra.scheduler;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Manager;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

import java.util.Date;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessSchedulerManager extends Manager {

	void scheduleAt(final Long proId, final Date planifiedTime, final Option<String> initialParams);

	DtList<OProcessPlanification> getProcessToExecute();

	void triggerPlanification(final OProcessPlanification processPlanification);

	void misfirePlanification(final OProcessPlanification processPlanification);

}
