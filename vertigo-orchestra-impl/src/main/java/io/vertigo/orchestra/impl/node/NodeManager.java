package io.vertigo.orchestra.impl.node;

import io.vertigo.lang.Manager;

/**
 * Interface de gestion des noeud d'execution orchestra.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface NodeManager extends Manager {

	/**
	 * Enregistre un noeud d'execution.
	 * @param nodeName son nom
	 * @return l'id du noeud
	 */
	Long registerNode(final String nodeName);

	/**
	 * Met à jour le status d'un noeud
	 * @param nodId l'id du noeud à mettre à jour
	 */
	void updateHeartbeat(final Long nodId);
}
