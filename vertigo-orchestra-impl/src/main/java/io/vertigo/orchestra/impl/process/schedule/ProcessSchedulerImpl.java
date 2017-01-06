package io.vertigo.orchestra.impl.process.schedule;

import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.process.execution.ProcessExecutor;
import io.vertigo.orchestra.process.schedule.ProcessScheduler;

/**
 * Implémentation du manager de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerImpl implements ProcessScheduler {
	private final Map<ProcessType, ProcessSchedulerPlugin> schedulerPluginsMap = new EnumMap<>(ProcessType.class);

	/**
	 * Constructeur.
	 * @param schedulerPlugins la liste des plugins de gestion de la planification
	 */
	public ProcessSchedulerImpl(final List<ProcessSchedulerPlugin> schedulerPlugins, final ProcessExecutor processExecutor) {
		Assertion.checkNotNull(schedulerPlugins);
		Assertion.checkNotNull(processExecutor);
		//---
		for (final ProcessSchedulerPlugin schedulerPlugin : schedulerPlugins) {
			//-1-- start
			schedulerPlugin.start(processExecutor);
			//-2-- register
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
	public void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Map<String, String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(planifiedTime);
		Assertion.checkNotNull(initialParams);
		// ---
		getPluginByType(processDefinition.getProcessType())
				.scheduleAt(processDefinition, planifiedTime, initialParams);
	}

	private ProcessSchedulerPlugin getPluginByType(final ProcessType processType) {
		final ProcessSchedulerPlugin schedulerPlugin = schedulerPluginsMap.get(processType);
		Assertion.checkNotNull(schedulerPlugin, "No plugin found for managing processType {0}", processType.name());
		return schedulerPlugin;
	}

}
