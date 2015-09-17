package io.vertigo.orchestra.impl.execution;

import java.util.Map;

import javax.inject.Inject;

import io.vertigo.core.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.execution.OTaskEngine;
import io.vertigo.orchestra.execution.OTaskManager;
import io.vertigo.orchestra.execution.ProcessExecutionManager;
import io.vertigo.util.ClassUtil;

/**
 * @author pchretien
 */
public final class OTaskManagerImpl implements OTaskManager {

	@Inject
	private ProcessExecutionManager processExecutionManager;

	/** {@inheritDoc} */
	@Override
	public Map<String, String> execute(final OTaskExecution taskExecution, final Map<String, String> params) {
		try {
			processExecutionManager.changeExecutionState(taskExecution, ExecutionState.RUNNING);
			// ---
			final OTaskEngine taskEngine = Injector.newInstance(
					ClassUtil.classForName(taskExecution.getEngine(), OTaskEngine.class), Home.getComponentSpace());
			return taskEngine.execute(params);
		} finally {
			//todo
		}
	}
}
