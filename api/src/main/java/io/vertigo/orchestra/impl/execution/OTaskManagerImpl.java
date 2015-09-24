package io.vertigo.orchestra.impl.execution;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import io.vertigo.core.Home;
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
		try {
			processExecutionManager.changeExecutionState(taskExecution, ExecutionState.RUNNING);
			// ---
			final OTaskEngine taskEngine = Injector.newInstance(
					ClassUtil.classForName(taskExecution.getEngine(), OTaskEngine.class), Home.getComponentSpace());
			return taskEngine.execute(workspace);
		} catch (final Exception e) {
			// In case of failure we return the current workspace
			logError(taskExecution, e);
			workspace.setFailure();
			return workspace;
		} finally {
			processExecutionManager.saveTaskExecutionWorkspace(taskExecution.getTkeId(), workspace, false);
		}

	}

	private void logError(final OTaskExecution taskExecution, final Throwable e) {
		LOGGER.error("Erreur de la tache : " + taskExecution.getEngine(), e);
	}
}
