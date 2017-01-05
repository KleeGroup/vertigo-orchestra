package io.vertigo.orchestra.process.execution;

import java.util.Optional;

import io.vertigo.orchestra.definition.ProcessDefinition;

public interface ProcessExecutor {
	/**
	 * Execute un processus.
	 * @param processDefinition le processus à lancer
	 * @param initialParams paramètres initiaux supplémentaires
	 */
	void execute(ProcessDefinition processDefinition, Optional<String> initialParams);

	/**
	 * Termine une execution mise en attente.
	 * @param activityExecutionId L'id de l'execution à terminer
	 * @param token Le ticket associé permettant de s'assurer que n'importe qui ne termine pas une activity (seulement un callback)
	 * @param state L'état futur de l'activité
	 */
	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state);

	/**
	 * Mets une execution en attente.
	 * @param activityExecutionId L'id de l'execution à mettre en attente
	 * @param workspace Le workspace avant la mise en attente
	 */
	void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace);

}
