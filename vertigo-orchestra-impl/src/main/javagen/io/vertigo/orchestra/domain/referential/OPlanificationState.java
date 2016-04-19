package io.vertigo.orchestra.domain.referential;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OPlanificationState
 */
@DtDefinition
public final class OPlanificationState implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String pstCd;
	private String label;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String pstCd <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_CODE_IDENTIFIANT", type = "ID", required = true, label = "Code")
	public String getPstCd() {
		return pstCd;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Code'.
	 * @param pstCd String <b>Obligatoire</b>
	 */
	public void setPstCd(final String pstCd) {
		this.pstCd = pstCd;
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


	// Association : ProcessPlanification non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
