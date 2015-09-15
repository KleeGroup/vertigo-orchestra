package io.vertigo.orchestra.services.planification;

import java.util.Date;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;
import io.vertigo.orchestra.services.OrchestraServices;

/**
 * API des services liés à la planification des processus.
 *
 * @author mlaroche
 */
public interface ProcessPlanificationServices extends OrchestraServices {

	public DtList<OProcessPlanification> getProcessToExecute();

	public void plannProcessAt(final Long proId, final Date planifiedTime);

	public DtList<OProcessPlanification> getProcessesPlanned(final Date from, final Date to);

	public DtList<OProcessPlanification> getPlanificationsByProcess(final Long proId);

	public Option<OProcessPlanification> getLastPlanificationsByProcess(final Long proId);

	public void triggerPlanification(final OProcessPlanification processPlanification);

}
