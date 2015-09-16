package io.vertigo.orchestra.impl.execution;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.orchestra.execution.ProcessExecutionManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessExecutionManagerInitializer implements ComponentInitializer<ProcessExecutionManager> {

	/** {@inheritDoc} */
	@Override
	public void init(final ProcessExecutionManager processExecutionManager) {
		processExecutionManager.postStart(processExecutionManager);

	}

}
