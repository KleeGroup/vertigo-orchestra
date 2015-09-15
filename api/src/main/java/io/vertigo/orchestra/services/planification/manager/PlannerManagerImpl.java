package io.vertigo.orchestra.services.planification.manager;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.services.planification.plugin.RecurrentProcessPlannerPlugin;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class PlannerManagerImpl implements PlannerManager {

	private final RecurrentProcessPlannerPlugin recurrentProcessPlannerPlugin;

	/**
	 * Constructeur.
	 *
	 * @param recurrentProcessPlannerPlugin
	 */
	@Inject
	public PlannerManagerImpl(final RecurrentProcessPlannerPlugin recurrentProcessPlannerPlugin) {
		Assertion.checkNotNull(recurrentProcessPlannerPlugin);
		// ---
		this.recurrentProcessPlannerPlugin = recurrentProcessPlannerPlugin;
	}

}
