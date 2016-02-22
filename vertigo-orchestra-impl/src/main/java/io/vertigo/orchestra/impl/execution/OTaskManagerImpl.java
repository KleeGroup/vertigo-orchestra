package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vertigo.app.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.OTaskManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.util.ClassUtil;

/**
 * @author mlaroche
 */
public final class OTaskManagerImpl implements OTaskManager {

	private static final Logger LOGGER = Logger.getLogger(OTaskManager.class);

	@Inject
	private ProcessExecutionManager processExecutionManager;

	/** {@inheritDoc} */
	@Override
	public TaskExecutionWorkspace execute(final OTaskExecution taskExecution, final TaskExecutionWorkspace workspace) {
		TaskExecutionWorkspace resultWorkspace = workspace;

		try {
			processExecutionManager.changeExecutionState(taskExecution, ExecutionState.RUNNING);
			// ---
			final OTaskEngine taskEngine = Injector.newInstance(
					ClassUtil.classForName(taskExecution.getEngine(), OTaskEngine.class), Home.getApp().getComponentSpace());

			try {
				// We try the execution and we keep the result
				resultWorkspace = taskEngine.execute(workspace);

			} catch (final Exception e) {
				// In case of failure we keep the current workspace
				resultWorkspace.setFailure();

			} finally {
				// We save the workspace which is the minimal state
				processExecutionManager.saveTaskExecutionWorkspace(taskExecution.getTkeId(), resultWorkspace, false);
				if (taskEngine instanceof AbstractOTaskEngine) {
					// If the engine extends the abstractEngine we can provide the services associated (LOGGING,...)
					processExecutionManager.saveTaskLogs(taskExecution.getTkeId(), ((AbstractOTaskEngine) taskEngine).getLogger());
				}
			}

		} catch (final Exception e) {
			// Informative log
			resultWorkspace.setFailure();
			logError(taskExecution, e);
		}

		return resultWorkspace;
	}

	private static void logError(final OTaskExecution taskExecution, final Throwable e) {
		LOGGER.error("Erreur de la tache : " + taskExecution.getEngine(), e);
	}
}
