package io.vertigo.orchestra.services.execution.manager;

import io.vertigo.orchestra.services.execution.plugin.DbSequentialCoordinatorPlugin;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OCoordinationManagerImpl implements OCoordinationManager {

	//	private final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin;

	/**
	 * Constructeur.
	 *
	 * @param dbSequentialDispatcherPlugin
	 */
	@Inject
	public OCoordinationManagerImpl(final DbSequentialCoordinatorPlugin dbSequentialCoordinatorPlugin) {
		//		Assertion.checkNotNull(dbSequentialCoordinatorPlugin);
		//		// ---
		//		this.dbSequentialCoordinatorPlugin = dbSequentialCoordinatorPlugin;

	}

}
