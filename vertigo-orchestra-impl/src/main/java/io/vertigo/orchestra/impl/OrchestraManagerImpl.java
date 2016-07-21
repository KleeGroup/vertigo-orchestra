package io.vertigo.orchestra.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 *
 * Orchestra high-level services implementation.
 * @author matth
 *
 */
public final class OrchestraManagerImpl implements OrchestraManager {

	private final ProcessDefinitionManager processDefinitionManager;
	private final ProcessSchedulerManager processSchedulerManager;
	private final ProcessExecutionManager processExecutionManager;

	@Inject
	public OrchestraManagerImpl(final ProcessDefinitionManager processDefinitionManager, final ProcessExecutionManager processExecutionManager, final ProcessSchedulerManager processSchedulerManager) {
		Assertion.checkNotNull(processDefinitionManager);
		Assertion.checkNotNull(processExecutionManager);
		Assertion.checkNotNull(processSchedulerManager);
		// ---
		this.processDefinitionManager = processDefinitionManager;
		this.processSchedulerManager = processSchedulerManager;
		this.processExecutionManager = processExecutionManager;
	}

	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		return processDefinitionManager.getProcessDefinition(processName);
	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinitionIfNeeded(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
	}

	@Override
	public List<ProcessDefinition> getAllProcesses() {
		return processDefinitionManager.getAllProcessDefinitions();
	}

	@Override
	public void scheduleNow(final String processName, final Optional<String> initialParams) {
		scheduleAt(processName, new Date(), initialParams);
	}

	@Override
	public void scheduleAt(final String processName, final Date expectedTime, final Optional<String> initialParams) {
		Assertion.checkNotNull(processName);
		Assertion.checkNotNull(expectedTime);
		// ---
		final ProcessDefinition processDefintion = getProcessDefinition(processName);
		Assertion.checkNotNull(processDefintion);
		// ---
		processSchedulerManager.scheduleAt(processDefintion.getId(), expectedTime, initialParams);
	}

	/** {@inheritDoc} */
	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		Assertion.checkNotNull(activityExecutionId);
		Assertion.checkNotNull(token);
		Assertion.checkNotNull(state);
		// ---
		processExecutionManager.endPendingActivityExecution(activityExecutionId, token, state);
	}

}
