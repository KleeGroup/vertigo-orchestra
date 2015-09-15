package io.vertigo.orchestra.planner;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.services.OrchestraServices;

import java.util.Date;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessPlannerManager extends OrchestraServices {

	DtList<OProcessPlanification> getProcessToExecute();

	void plannProcessAt(final Long proId, final Date planifiedTime);

	DtList<OProcessPlanification> getProcessesPlanned(final Date from, final Date to);

	DtList<OProcessPlanification> getPlanificationsByProcess(final Long proId);

	Option<OProcessPlanification> getLastPlanificationsByProcess(final Long proId);

	void triggerPlanification(final OProcessPlanification processPlanification);
}
