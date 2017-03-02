package io.vertigo.orchestra.impl.services;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.impl.services.execution.ProcessExecutorImpl;
import io.vertigo.orchestra.impl.services.execution.ProcessExecutorPlugin;
import io.vertigo.orchestra.impl.services.schedule.ProcessSchedulerImpl;
import io.vertigo.orchestra.impl.services.schedule.ProcessSchedulerPlugin;
import io.vertigo.orchestra.services.OrchestraServices;
import io.vertigo.orchestra.services.execution.ProcessExecutor;
import io.vertigo.orchestra.services.log.ProcessLogger;
import io.vertigo.orchestra.services.report.ProcessReport;
import io.vertigo.orchestra.services.schedule.ProcessScheduler;

/**
 * Implémentation du manager des executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class OrchestraServicesImpl implements OrchestraServices {

	private final ProcessExecutor processExecutor;
	private final Optional<ProcessLogger> optionalProcessLog;
	private final Optional<ProcessReport> optionalProcessReport;
	private final ProcessScheduler processScheduler;

	/**
	 * Constructeur du gestionnaire de l'execution des processus orchestra.
	 * @param processExecutorPlugins plugins of execution
	 * @param logProviderPlugin plugin for logging
	 * @param processReportPlugin plugin for reporting
	 * @param processSchedulerPlugins plugins for scheduling
	 */
	@Inject
	public OrchestraServicesImpl(
			final List<ProcessExecutorPlugin> processExecutorPlugins,
			final Optional<ProcessLoggerPlugin> logProviderPlugin,
			final Optional<ProcessReportPlugin> processReportPlugin,
			final List<ProcessSchedulerPlugin> processSchedulerPlugins) {
		Assertion.checkNotNull(processExecutorPlugins);
		Assertion.checkNotNull(logProviderPlugin);
		Assertion.checkNotNull(processReportPlugin);
		Assertion.checkNotNull(processSchedulerPlugins);
		// ---
		processExecutor = new ProcessExecutorImpl(processExecutorPlugins);
		processScheduler = new ProcessSchedulerImpl(processSchedulerPlugins, processExecutor);
		optionalProcessLog = Optional.ofNullable(logProviderPlugin.orElse(null));
		optionalProcessReport = Optional.ofNullable(processReportPlugin.orElse(null));
	}

	/** {@inheritDoc} */
	@Override
	public ProcessExecutor getExecutor() {
		return processExecutor;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessScheduler getScheduler() {
		return processScheduler;
	}

	@Override
	public ProcessLogger getLogger() {
		return optionalProcessLog
				.orElseThrow(() -> new IllegalStateException("A ProcessLogPlugin must be defined for logging"));
	}

	@Override
	public ProcessReport getReport() {
		return optionalProcessReport
				.orElseThrow(() -> new IllegalStateException("A ProcessReportPlugin must be defined for retrieving executions and summaries"));
	}

}
