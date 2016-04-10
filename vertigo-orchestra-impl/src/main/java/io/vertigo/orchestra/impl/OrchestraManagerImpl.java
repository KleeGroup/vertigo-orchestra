package io.vertigo.orchestra.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 *
 * Orchestra high-level services implementation.
 * @author matth
 *
 */
public class OrchestraManagerImpl implements OrchestraManager {

	private final ProcessDefinitionManager processDefinitionManager;
	private final ProcessSchedulerManager processSchedulerManager;

	@Inject
	public OrchestraManagerImpl(final ProcessDefinitionManager processDefinitionManager, final ProcessExecutionManager processExecutionManager, final ProcessSchedulerManager processSchedulerManager) {
		Assertion.checkNotNull(processDefinitionManager);
		Assertion.checkNotNull(processExecutionManager);
		Assertion.checkNotNull(processSchedulerManager);
		// ---
		this.processDefinitionManager = processDefinitionManager;
		this.processSchedulerManager = processSchedulerManager;
	}

	@Override
	public void createDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		processDefinitionManager.createDefinition(processDefinition);

	}

	@Override
	public ProcessDefinition getProcessDefinition(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		return processDefinitionManager.getProcessDefinition(processName);
	}

	/** {@inheritDoc} */
	@Override
	public boolean processDefinitionExist(final String processName) {
		Assertion.checkNotNull(processName);
		// ---
		return processDefinitionManager.processDefinitionExist(processName);
	}

	@Override
	public List<ProcessDefinition> getAllProcesses() {
		return processDefinitionManager.getAllProcessDefinitions();
	}

	@Override
	public void scheduleNow(final String processName, final Option<String> initialParams) {
		scheduleAt(processName, new Date(), initialParams);
	}

	@Override
	public void scheduleAt(final String processName, final Date expectedTime, final Option<String> initialParams) {
		Assertion.checkNotNull(processName);
		Assertion.checkNotNull(expectedTime);
		// ---
		final ProcessDefinition processDefintion = getProcessDefinition(processName);
		Assertion.checkNotNull(processDefintion);
		// ---
		processSchedulerManager.scheduleAt(processDefintion.getId(), expectedTime, initialParams);
	}

}