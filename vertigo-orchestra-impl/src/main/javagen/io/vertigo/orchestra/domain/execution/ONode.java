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
package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données ONode
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("orchestra")
public final class ONode implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long nodId;
	private String name;
	private java.util.Date heartbeat;

	/** {@inheritDoc} */
	@Override
	public URI<ONode> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id du noeud'.
	 * @return Long nodId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "ID", required = true, label = "Id du noeud")
	public Long getNodId() {
		return nodId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id du noeud'.
	 * @param nodId Long <b>Obligatoire</b>
	 */
	public void setNodId(final Long nodId) {
		this.nodId = nodId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du noeud'.
	 * @return String name <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_LIBELLE", required = true, label = "Nom du noeud")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du noeud'.
	 * @param name String <b>Obligatoire</b>
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date de dernière activité'.
	 * @return java.util.Date heartbeat
	 */
	@Field(domain = "DO_O_TIMESTAMP", label = "Date de dernière activité")
	public java.util.Date getHeartbeat() {
		return heartbeat;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date de dernière activité'.
	 * @param heartbeat java.util.Date
	 */
	public void setHeartbeat(final java.util.Date heartbeat) {
		this.heartbeat = heartbeat;
	}

	// Association : ExecutionActivity non navigable
	// Association : PlanificationProcessus non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
