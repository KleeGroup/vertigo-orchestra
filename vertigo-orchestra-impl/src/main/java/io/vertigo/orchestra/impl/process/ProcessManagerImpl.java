package io.vertigo.orchestra.impl.process;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.impl.process.execution.ProcessExecutorImpl;
import io.vertigo.orchestra.impl.process.execution.ProcessExecutorPlugin;
import io.vertigo.orchestra.impl.process.schedule.ProcessSchedulerImpl;
import io.vertigo.orchestra.impl.process.schedule.ProcessSchedulerPlugin;
import io.vertigo.orchestra.process.ProcessManager;
import io.vertigo.orchestra.process.execution.ProcessExecutor;
import io.vertigo.orchestra.process.log.ProcessLogger;
import io.vertigo.orchestra.process.report.ProcessReport;
import io.vertigo.orchestra.process.schedule.ProcessScheduler;

/**
 * Implémentation du manager des executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessManagerImpl implements ProcessManager {

	private final ProcessExecutor processExecutor;
	private final Optional<ProcessLogger> optionalProcessLog;
	private final Optional<ProcessReport> optionalProcessReport;
	private final ProcessScheduler processScheduler;

	/**
	 * Constructeur du gestionnaire de l'execution des processus orchestra
	 * @param executorPlugins liste des plugins d'execution
	 * @param logProviderPlugin plugin de gestion des logs
	 */
	@Inject
	public ProcessManagerImpl(
			final List<ProcessExecutorPlugin> processExecutorPlugins,
			final Optional<ProcessLoggerPlugin> logProviderPlugin,
			final Optional<ProcessReportPlugin> processReportPlugin,
			final List<ProcessSchedulerPlugin> processSchedulerPlugins) {
		Assertion.checkNotNull(processExecutorPlugins);
		Assertion.checkNotNull(logProviderPlugin);
		Assertion.checkNotNull(processReportPlugin);
		Assertion.checkNotNull(processSchedulerPlugins);
		// ---
		this.processExecutor = new ProcessExecutorImpl(processExecutorPlugins);
		this.processScheduler = new ProcessSchedulerImpl(processSchedulerPlugins, processExecutor);
		this.optionalProcessLog = Optional.ofNullable(logProviderPlugin.orElse(null));
		this.optionalProcessReport = Optional.ofNullable(processReportPlugin.orElse(null));
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
		return optionalProcessLog.orElseThrow(() -> new IllegalStateException("A ProcessLogPlugin must be defined for logging"));
	}

	@Override
	public ProcessReport getReport() {
		return optionalProcessReport.orElseThrow(() -> new IllegalStateException("A ProcessReportPlugin must be defined for retrieving executions and summaries"));
	}

}
