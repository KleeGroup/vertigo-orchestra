package io.vertigo.orchestra.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.scheduler.SchedulerManager;

/**
 *
 * Orchestra high-level services implementation.
 * @author matth
 *
 */
public final class OrchestraManagerImpl implements OrchestraManager {

	private final ProcessDefinitionManager processDefinitionManager;
	private final SchedulerManager schedulerManager;

	/**
	 * Manager principal d'orchestra.
	 * @param processDefinitionManager le manager responsable des définitions
	 * @param shedulerManager le manager responsable de la planification
	 */
	@Inject
	public OrchestraManagerImpl(final ProcessDefinitionManager processDefinitionManager, final SchedulerManager shedulerManager) {
		Assertion.checkNotNull(processDefinitionManager);
		Assertion.checkNotNull(shedulerManager);
		// ---
		this.processDefinitionManager = processDefinitionManager;
		this.schedulerManager = shedulerManager;
	}

	/** {@inheritDoc} */
	@Override
	public void createOrUpdateDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		processDefinitionManager.createOrUpdateDefinitionIfNeeded(processDefinition);
	}

	/** {@inheritDoc} */
	@Override
	public List<ProcessDefinition> getAllProcessDefinitions() {
		return processDefinitionManager.getAllProcessDefinitions();
	}

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final String processName, final Date expectedTime, final Optional<String> initialParams) {
		Assertion.checkNotNull(processName);
		Assertion.checkNotNull(expectedTime);
		// ---
		final ProcessDefinition processDefinition = processDefinitionManager.getProcessDefinition(processName);
		Assertion.checkNotNull(processDefinition);
		// ---
		schedulerManager.scheduleAt(processDefinition, expectedTime, initialParams);
	}

	/** {@inheritDoc} */
	@Override
	public void scheduleWithCron(final String processName) {
		Assertion.checkArgNotEmpty(processName);
		// ---
		final ProcessDefinition processDefinition = processDefinitionManager.getProcessDefinition(processName);
		Assertion.checkNotNull(processDefinition);
		// ---
		schedulerManager.scheduleWithCron(processDefinition);

	}

}
