package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données ONode
 */
@DtDefinition
public final class ONode implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long nodId;
	private String name;
	private java.util.Date heartbeat;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id du noeud'. 
	 * @return Long nodId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "Id du noeud")
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
	@Field(domain = "DO_LIBELLE", required = true, label = "Nom du noeud")
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
	 * @return java.util.Date heartbeat <b>Obligatoire</b>
	 */
	@Field(domain = "DO_TIMESTAMP", required = true, label = "Date de dernière activité")
	public java.util.Date getHeartbeat() {
		return heartbeat;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date de dernière activité'.
	 * @param heartbeat java.util.Date <b>Obligatoire</b>
	 */
	public void setHeartbeat(final java.util.Date heartbeat) {
		this.heartbeat = heartbeat;
	}


	// Association : PlanificationProcessus non navigable

	// Association : ExecutionActivity non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
