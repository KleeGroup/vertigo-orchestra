package io.vertigo.orchestra.services.execution.manager;

import io.vertigo.core.Home;
import io.vertigo.core.component.di.injector.Injector;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.domain.execution.OTaskExecution;
import io.vertigo.orchestra.services.execution.ExecutionServices;
import io.vertigo.orchestra.services.execution.engine.OTaskEngine;
import io.vertigo.util.ClassUtil;

import java.util.Map;

import javax.inject.Inject;

/**
 * @author pchretien
 */
public final class OTaskManagerImpl implements OTaskManager {

	@Inject
	private ExecutionServices executionServices;

	/** {@inheritDoc} */
	@Override
	public Map<String, String> execute(final OTaskExecution taskExecution, final Map<String, String> params) {
		try {
			Assertion.checkNotNull(taskExecution);
			// ---
			taskExecution.setEstCd("RUNNING");
			executionServices.saveTaskExecution(taskExecution);
			// ---
			final OTaskEngine taskEngine = Injector.newInstance(
					ClassUtil.classForName(taskExecution.getEngine(), OTaskEngine.class), Home.getComponentSpace());
			return taskEngine.execute(params);
		} finally {
			//todo
		}
	}
}
