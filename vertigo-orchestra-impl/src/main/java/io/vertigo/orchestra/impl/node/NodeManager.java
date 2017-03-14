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
