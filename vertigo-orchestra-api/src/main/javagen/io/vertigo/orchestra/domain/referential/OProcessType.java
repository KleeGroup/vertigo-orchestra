package io.vertigo.orchestra.domain.referential;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OProcessType
 */
@DtDefinition
public final class OProcessType implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private String prtCd;
	private String label;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Code'. 
	 * @return String prtCd <b>Obligatoire</b>
	 */
	@Field(domain = "DO_CODE_IDENTIFIANT", type = "ID", required = true, label = "Code")
	public String getPrtCd() {
		return prtCd;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Code'.
	 * @param prtCd String <b>Obligatoire</b>
	 */
	public void setPrtCd(final String prtCd) {
		this.prtCd = prtCd;
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


	// Association : Process non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}