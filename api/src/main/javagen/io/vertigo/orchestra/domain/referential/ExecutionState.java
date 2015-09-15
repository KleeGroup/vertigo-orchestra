package io.vertigo.orchestra.domain.referential;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données ExecutionState
 */
@DtDefinition
public final class ExecutionState implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String estCd;
	private String label;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String estCd <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "Code")
	public String getEstCd() {
		return estCd;
	}

	/**
	 * Champ : PRIMARY_KEY.
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
	@Field(domain = "DO_LIBELLE", label = "Libellé")
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

	// Association : ExecutionTache non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
