package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.impl.scheduler.plugins.ProcessSchedulerPlugin;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 * Implémentation du manager de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerManagerImpl implements ProcessSchedulerManager {
	private final Map<ProcessType, ProcessSchedulerPlugin> schedulerPluginsMap = new HashMap<>();

	/**
	 * Constructeur.
	 * @param transactionManager le gestionnaire de transaction
	 * @param processScheduler le gestionnaire de schedule
	 */
	@Inject
	public ProcessSchedulerManagerImpl(
			final List<ProcessSchedulerPlugin> schedulerPlugins) {
		for (final ProcessSchedulerPlugin schedulerPlugin : schedulerPlugins) {
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
		getPluginByType(processDefinition.getProcessType()).scheduleWithCron(processDefinition);

	}

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParamsOption) {
		Assertion.checkNotNull(processDefinition);
		Assertion.checkNotNull(planifiedTime);
		// ---
		getPluginByType(processDefinition.getProcessType()).scheduleAt(processDefinition, planifiedTime, initialParamsOption);
	}

	private ProcessSchedulerPlugin getPluginByType(final ProcessType processType) {
		final ProcessSchedulerPlugin schedulerPlugin = schedulerPluginsMap.get(processType);
		Assertion.checkNotNull(schedulerPlugin, "No plugin found for managing processType {0}", processType.name());
		return schedulerPlugin;
	}

}
