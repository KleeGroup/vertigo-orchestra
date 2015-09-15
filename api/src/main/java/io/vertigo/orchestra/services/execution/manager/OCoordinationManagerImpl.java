package io.vertigo.orchestra.services.execution.manager;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.services.execution.plugin.DbSequentialCoordinatorPlugin;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OCoordinationManagerImpl implements OCoordinationManager {

	private final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin;

	/**
	 * Constructeur.
	 *
	 * @param dbSequentialDispatcherPlugin
	 */
	@Inject
	public OCoordinationManagerImpl(final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin) {
		Assertion.checkNotNull(dbSequentialCoordinatorPlugin);
		// ---
		this.dbSequentialCoordinatorPlugin = dbSequentialCoordinatorPlugin;

	}

}
