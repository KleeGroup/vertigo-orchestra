package io.vertigo.orchestra.services.execution;

import java.util.Optional;

import io.vertigo.orchestra.definitions.ProcessDefinition;

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
	 * @param errorMessage an optional ErrorMessage
	 *
	 */
	void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state, final Optional<String> errorMessageOpt);

	/**
	 * Mets une execution en attente.
	 * @param activityExecutionId L'id de l'execution à mettre en attente
	 * @param workspace Le workspace avant la mise en attente
	 */
	void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace);

}
