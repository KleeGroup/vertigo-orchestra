package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.scheduler.SchedulerManager;

/**
 * Implémentation du manager de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class SchedulerManagerImpl implements SchedulerManager {
	private final Map<ProcessType, SchedulerPlugin> schedulerPluginsMap = new EnumMap<>(ProcessType.class);

	/**
	 * Constructeur.
	 * @param schedulerPlugins la liste des plugins de gestion de la planification
	 */
	@Inject
	public SchedulerManagerImpl(final List<SchedulerPlugin> schedulerPlugins) {
		Assertion.checkNotNull(schedulerPlugins);
		//---
		for (final SchedulerPlugin schedulerPlugin : schedulerPlugins) {
			Assertion.checkState(!schedulerPluginsMap.containsKey(schedulerPlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}", schedulerPlugin.getHandledProcessType());
			schedulerPluginsMap.put(schedulerPlugin.getHandledProcessType(), schedulerPlugin);
		}
	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	@Override
	public void scheduleWithCron(final ProcessDefinition processDefinition) {
		Assertion.checkNotNull(processDefinition);
		// ---
		getPluginByType(processDefinition.getProcessType())
				.scheduleWithCron(processDefinition);

	}

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParamsOption) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(planifiedTime);
		Assertion.checkNotNull(initialParamsOption);
		// ---
		getPluginByType(processDefinition.getProcessType())
				.scheduleAt(processDefinition, planifiedTime, initialParamsOption);
	}

	private SchedulerPlugin getPluginByType(final ProcessType processType) {
		final SchedulerPlugin schedulerPlugin = schedulerPluginsMap.get(processType);
		Assertion.checkNotNull(schedulerPlugin, "No plugin found for managing processType {0}", processType.name());
		return schedulerPlugin;
	}

}
