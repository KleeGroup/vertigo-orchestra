package io.vertigo.orchestra.impl.execution.plugins.simple;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.definition.ActivityDefinition;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;
import io.vertigo.orchestra.execution.ActivityEngine;
import io.vertigo.orchestra.execution.ActivityExecutionWorkspace;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.impl.execution.AbstractActivityEngine;
import io.vertigo.orchestra.impl.execution.plugins.ProcessExecutorPlugin;

public class SimpleExecutorPlugin implements ProcessExecutorPlugin {

	private static final Logger LOGGER = Logger.getLogger(SimpleExecutorPlugin.class);

	final ExecutorService localExecutor = Executors.newFixedThreadPool(5);

	@Override
	public void execute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		localExecutor.submit(() -> doSequentialExecute(processDefinition, initialParams));

	}

	private static void doSequentialExecute(final ProcessDefinition processDefinition, final Optional<String> initialParams) {
		final ActivityExecutionWorkspace initialWorkspace = new ActivityExecutionWorkspace(processDefinition.getInitialParams().orElse(null));
		if (initialParams.isPresent()) {
			initialWorkspace.addExternalParams(initialParams.get());
		}

		ActivityExecutionWorkspace resultWorkspace = initialWorkspace;
		for (final ActivityDefinition activityDefinition : processDefinition.getActivities()) {
			resultWorkspace = executeActivity(activityDefinition, resultWorkspace);
			if (resultWorkspace.isFailure()) {
				break;
			}
		}

	}

	private static ActivityExecutionWorkspace executeActivity(final ActivityDefinition activityDefinition, final ActivityExecutionWorkspace workspaceIn) {
		ActivityExecutionWorkspace resultWorkspace = workspaceIn;
		try {
			// ---
			final ActivityEngine activityEngine = Injector.newInstance(activityDefinition.getEngineClass(), Home.getApp().getComponentSpace());

			try {

				// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...) so we log the workspace
				if (activityEngine instanceof AbstractActivityEngine) {
					final String workspaceInLog = new StringBuilder("Workspace in :").append(workspaceIn.getStringForStorage()).toString();
					((AbstractActivityEngine) activityEngine).getLogger().info(workspaceInLog);
				}
				// We try the execution and we keep the result
				resultWorkspace = activityEngine.execute(workspaceIn);
				Assertion.checkNotNull(resultWorkspace);
				Assertion.checkNotNull(resultWorkspace.getValue("status"), "Le status est obligatoire dans le résultat");
				// we call the posttreament
				resultWorkspace = activityEngine.successfulPostTreatment(resultWorkspace);

			} catch (final Exception e) {
				// In case of failure we keep the current workspace
				resultWorkspace.setFailure();
				LOGGER.error("Erreur de l'activité : " + activityDefinition.getEngineClass().getName(), e);
				// we call the posttreament
				resultWorkspace = activityEngine.errorPostTreatment(resultWorkspace, e);

			}

		} catch (final Exception e) {
			// Informative log
			resultWorkspace.setFailure();
			LOGGER.error("Erreur de l'activité : " + activityDefinition.getEngineClass().getName(), e);
		}
		return resultWorkspace;
	}

	@Override
	public ProcessType getHandledProcessType() {
		return ProcessType.UNSUPERVISED;
	}

	@Override
	public void endPendingActivityExecution(final Long activityExecutionId, final String token, final ExecutionState state) {
		// unsupported
		throw new UnsupportedOperationException("A daemon execution cannot be set pending");

	}

	@Override
	public void setActivityExecutionPending(final Long activityExecutionId, final ActivityExecutionWorkspace workspace) {
		// unsupported
		throw new UnsupportedOperationException("A daemon execution cannot be set pending");

	}

}
