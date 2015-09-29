package io.vertigo.orchestra.monitoring;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.execution.OExecutionWorkspace;
import io.vertigo.orchestra.domain.execution.OProcessExecution;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * Services permettant de suivre le fonctionnement de la tour de controle.
 * @author mlaroche.
 */
public interface MonitoringServices {

	/**
	 * Récupère la liste des processus définis.
	 * @return la liste des processus.
	 */
			DtList<OProcess> getProcesses();

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
			DtList<OTaskExecution> getTaskExecutionsByPreId(Long preId);

	/**
	 * Récupère un workspace associé à une execution de tâche.
	 * @param tkeId l'id d'execution de tâche
	 * @param isIn true : workspace entrant, false : workspace sortant
	 * @return un workspace de tâche.
	 */
			OExecutionWorkspace geExecutionWorkspaceByTkeId(Long tkeId, final boolean isIn);

}
