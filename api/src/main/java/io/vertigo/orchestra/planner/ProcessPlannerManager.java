package io.vertigo.orchestra.planner;

import java.util.Date;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.PostActiveable;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessPlannerManager extends PostActiveable<ProcessPlannerManager> {

	void plannProcessAt(final Long proId, final Date planifiedTime, final String initialParams);

	void plannProcessAt(final Long proId, final Date planifiedTime);

	void plannRecurrentProcesses();

	DtList<OProcessPlanification> getProcessToExecute();

	void triggerPlanification(final OProcessPlanification processPlanification);

	void misfirePlanification(final OProcessPlanification processPlanification);

}
