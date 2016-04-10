package io.vertigo.orchestra.impl.execution;

import java.util.Date;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.execution.ONodeDAO;
import io.vertigo.orchestra.domain.execution.ONode;
import io.vertigo.orchestra.execution.NodeManager;

/**
 * TODO : Description de la classe.
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
		Assertion.checkNotNull(nodeName);
		// ---
		final Option<ONode> existingNode = nodeDAO.getNodeByName(nodeName);
		final ONode node = existingNode.getOrElse(new ONode());
		if (existingNode.isDefined()) {
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