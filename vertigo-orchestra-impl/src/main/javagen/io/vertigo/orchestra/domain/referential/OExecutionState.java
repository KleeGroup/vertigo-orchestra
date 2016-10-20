package io.vertigo.orchestra.domain.referential;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OExecutionState
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("orchestra")
public final class OExecutionState implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String estCd;
	private String label;

	/** {@inheritDoc} */
	@Override
	public URI<OExecutionState> getURI() {
		return DtObjectUtil.createURI(this);
	}
	
	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String estCd <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_CODE_IDENTIFIANT", type = "ID", required = true, label = "Code")
	public String getEstCd() {
		return estCd;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Code'.
	 * @param estCd String <b>Obligatoire</b>
	 */
	public void setEstCd(final String estCd) {
		this.estCd = estCd;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String label
	 */
	@Field(domain = "DO_O_LIBELLE", label = "Libellé")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé'.
	 * @param label String
	 */
	public void setLabel(final String label) {
		this.label = label;
	}


	// Association : ExecutionProcessus non navigable

	// Association : ExecutionActivity non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
