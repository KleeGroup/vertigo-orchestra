package io.vertigo.orchestra.impl.planner;

import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.orchestra.planner.ProcessPlannerManager;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessPlannerManagerInitializer implements ComponentInitializer<ProcessPlannerManager> {

	/** {@inheritDoc} */
	@Override
	public void init(final ProcessPlannerManager processPlannerManager) {
		processPlannerManager.postStart(processPlannerManager);

	}

}
