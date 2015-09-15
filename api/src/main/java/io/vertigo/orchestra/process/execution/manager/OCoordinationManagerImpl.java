package io.vertigo.orchestra.process.execution.manager;

import io.vertigo.orchestra.plugins.execution.DbSequentialCoordinatorPlugin;

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
