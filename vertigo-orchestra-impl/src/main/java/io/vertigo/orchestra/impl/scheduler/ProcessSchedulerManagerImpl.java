package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.scheduler.ProcessSchedulerManager;

/**
 * Implémentation du manager de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessSchedulerManagerImpl implements ProcessSchedulerManager {
	private final ProcessSchedulerPlugin schedulerPlugin;

	/**
	 * Constructeur.
	 * @param transactionManager le gestionnaire de transaction
	 * @param processScheduler le gestionnaire de schedule
	 */
	@Inject
	public ProcessSchedulerManagerImpl(
			final ProcessSchedulerPlugin schedulerPlugin) {
		Assertion.checkNotNull(schedulerPlugin);
		//---
		this.schedulerPlugin = schedulerPlugin;
	}

	//--------------------------------------------------------------------------------------------------
	//--- Public
	//--------------------------------------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParamsOption) {
		schedulerPlugin.scheduleAt(processDefinition, planifiedTime, initialParamsOption);
	}

	/** {@inheritDoc} */

	/** {@inheritDoc} */
	@Override
	public Map<ProcessDefinition, String> getProcessToExecute() {
		return schedulerPlugin.getProcessToExecute();
	}

}
