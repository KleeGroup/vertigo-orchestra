package io.vertigo.orchestra.impl.execution;

import java.util.Date;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.file.model.VFile;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.orchestra.execution.activity.ActivityExecution;
import io.vertigo.orchestra.execution.activity.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.process.ExecutionSummary;
import io.vertigo.orchestra.execution.process.ProcessExecution;

/**
 * Implémentation du manager des executions.
 *
 * @author mlaroche.
 * @version $Id$
 */
public final class ProcessExecutionManagerImpl implements ProcessExecutionManager {

	private final Map<ProcessType, ProcessExecutorPlugin> executorPluginsMap = new EnumMap<>(ProcessType.class);
	private final Optional<LogProviderPlugin> logProviderPlugin;
	private final Optional<ProcessReportPlugin> processReportPlugin;

	/**
	 * Constructeur du gestionnaire de l'execution des processus orchestra
	 * @param executorPlugins liste des plugins d'execution
	 * @param logProviderPlugin plugin de gestion des logs
	 */
	@Inject
	public ProcessExecutionManagerImpl(final List<ProcessExecutorPlugin> executorPlugins, final Optional<LogProviderPlugin> logProviderPlugin,
			final Optional<ProcessReportPlugin> processReportPlugin) {
		Assertion.checkNotNull(logProviderPlugin);
		Assertion.checkNotNull(processReportPlugin);
		// ---
		for (final ProcessExecutorPlugin executorPlugin : executorPlugins) {
			Assertion.checkState(!executorPluginsMap.containsKey(executorPlugin.getHandledProcessType()), "Only one plugin can manage the processType {0}", executorPlugin.getHandledProcessType());
			executorPluginsMap.put(executorPlugin.getHandledProcessType(), executorPlugin);
		}
		this.logProviderPlugin = logProviderPlugin;
		this.processReportPlugin = processReportPlugin;
	}

	/** {@inheritDoc} */
	@Override
	public void execute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		Assertion.checkNotNull(processDefinition);
		// ---
		getPluginByType(processDefinition.getProcessType()).execute(processDefinition, initialParams);
	}

	/** {@inheritDoc} */
	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		// Only Supervised can be pending
		getPluginByType(ProcessType.SUPERVISED).endPendingActivityExecution(activityExecutionId, token, state);
	}

	/** {@inheritDoc} */
	@Override
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		// Only Supervised can be pending
		getPluginByType(ProcessType.SUPERVISED).setActivityExecutionPending(activityExecutionId, workspace);
	}

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getLogFileForProcess(final Long processExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getLogFileForProcess(processExecutionId);
		}
		return Optional.<VFile> empty();
	}

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getLogFileForActivity(final Long actityExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getLogFileForActivity(actityExecutionId);
		}
		return Optional.<VFile> empty();
	}

	/** {@inheritDoc} */
	@Override
	public Optional<VFile> getTechnicalLogFileForActivity(final Long actityExecutionId) {
		if (logProviderPlugin.isPresent()) {
			return logProviderPlugin.get().getTechnicalLogFileForActivity(actityExecutionId);
		}
		return Optional.<VFile> empty();
	}

	/****************************************************************************************************************/
	/**                                           Report                                                           **/
	/****************************************************************************************************************/

	@Override
	public List<ProcessExecution> getProcessExecutions(final ProcessDefinition processDefinition, final String status, final Integer limit, final Integer offset) {
		checkProcessDefinition(processDefinition);
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getProcessExecutions(processDefinition, status, limit, offset);

	}

	@Override
	public List<ExecutionSummary> getSummariesByDate(final Date minDate, final Date maxDate, final String status) {
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getSummariesByDate(minDate, maxDate, status);
	}

	@Override
	public ExecutionSummary getSummaryByDateAndName(final ProcessDefinition processDefinition, final Date minDate, final Date maxDate) {
		checkProcessDefinition(processDefinition);
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getSummaryByDateAndName(processDefinition, minDate, maxDate);
	}

	@Override
	public ProcessExecution getProcessExecution(final Long preId) {
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getProcessExecution(preId);
	}

	@Override
	public List<ActivityExecution> getActivityExecutionsByProcessExecution(final Long preId) {
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getActivityExecutionsByProcessExecution(preId);
	}

	@Override
	public ActivityExecution getActivityExecution(final Long aceId) {
		checkPluginPresent();
		// ---
		return processReportPlugin.get().getActivityExecution(aceId);
	}

	private static void checkProcessDefinition(final ProcessDefinition processDefinition) {
		Assertion.checkState(ProcessType.SUPERVISED.equals(processDefinition.getProcessType()), "Only supervised process can retrieve executions. Process {0} isn't", processDefinition.getName());
	}

	private void checkPluginPresent() {
		Assertion.checkState(processReportPlugin.isPresent(), "A ProcessReportPlugin must be defined for retrieving executions and summaries");
	}

	/****************************************************************************************************************/
	/**                                           Util                                                             **/
	/****************************************************************************************************************/
	private ProcessExecutorPlugin getPluginByType(final ProcessType processType) {
		final ProcessExecutorPlugin executorPlugin = executorPluginsMap.get(processType);
		Assertion.checkNotNull(executorPlugin, "No plugin found for managing processType {0}", processType.name());
		return executorPlugin;
	}

}
