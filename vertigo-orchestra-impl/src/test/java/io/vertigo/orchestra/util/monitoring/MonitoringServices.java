package io.vertigo.orchestra.util.monitoring;

import java.util.Optional;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Component;
import io.vertigo.orchestra.domain.execution.OActivityExecution;
import io.vertigo.orchestra.domain.execution.OActivityLog;
import io.vertigo.orchestra.domain.execution.OActivityWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * Services permettant de suivre le fonctionnement de la tour de controle.
 * @author mlaroche.
 */
public interface MonitoringServices extends Component {

	/**
	 * Récupère la liste des planifications liés à un processus.
	 * @param proId l'id du processus
	 * @return la liste des planification concernant ce processus.
	 */
	DtList<OProcessPlanification> getPlanificationsByProId(Long proId);

	/**
	 * Récupère la liste des executions d'un processus.
	 * @param proId
	 * @return la liste des executions concernant ce processus.
	 */
	DtList<OProcessExecution> getExecutionsByProId(Long proId);

	/**
	 * Récupère la liste des tâches executées lors d'une execution de processus.
	 * @param preId l'id de l'execution de processus
	 * @return la liste des tâches executées pour cette execution de processus.
	 */
	DtList<OActivityExecution> getActivityExecutionsByPreId(Long preId);

	/**
	 * Récupère un workspace associé à une execution de tâche.
	 * @param aceId l'id d'execution de tâche
	 * @param isIn true : workspace entrant, false : workspace sortant
	 * @return un workspace de tâche.
	 */
	OActivityWorkspace getActivityWorkspaceByAceId(Long aceId, final boolean isIn);

	/**
	 * Récupère un log associé à une execution de tâche.
	 * @param aceId l'id d'execution de tâche
	 * @return un workspace de tâche.
	 */
	Optional<OActivityLog> getActivityLogByAceId(Long aceId);

}
