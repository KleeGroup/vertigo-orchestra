package io.vertigo.orchestra.execution;

import io.vertigo.lang.Manager;

/**
 * TODO : Description de la classe.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface NodeManager extends Manager {

	Long registerNode(final String nodeName);

	void updateHeartbeat(final Long nodId);
}
