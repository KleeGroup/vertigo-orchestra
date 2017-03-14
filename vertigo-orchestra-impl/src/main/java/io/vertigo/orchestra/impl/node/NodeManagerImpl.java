/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2017, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
