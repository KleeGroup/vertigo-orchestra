package io.vertigo.orchestra.impl.node;

import java.util.Date;
import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.execution.ONodeDAO;
import io.vertigo.orchestra.domain.execution.ONode;
import io.vertigo.orchestra.impl.node.NodeManager;

/**
 * Implémentation du gestionnaire de noeuds.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class NodeManagerImpl implements NodeManager {

	@Inject
	private ONodeDAO nodeDAO;

	@Override
	public Long registerNode(final String nodeName) {
		Assertion.checkArgNotEmpty(nodeName);
		// ---
		final Optional<ONode> existingNode = nodeDAO.getNodeByName(nodeName);
		final ONode node = existingNode.orElse(new ONode());
		if (existingNode.isPresent()) {
			nodeDAO.update(node);
		} else {
			node.setName(nodeName);
			nodeDAO.create(node);
		}
		return node.getNodId();

	}

	@Override
	public void updateHeartbeat(final Long nodId) {
		final ONode node = nodeDAO.get(nodId);
		node.setHeartbeat(new Date());
		nodeDAO.update(node);

	}

}
