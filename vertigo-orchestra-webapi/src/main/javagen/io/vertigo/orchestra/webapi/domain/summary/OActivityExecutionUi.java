package io.vertigo.orchestra.webapi.domain.summary;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OActivityExecutionUi
 */
@DtDefinition(persistent = false)
public final class OActivityExecutionUi implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long aceId;
	private String label;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private Long executionTime;
	private String status;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id de l'activité'. 
	 * @return Long aceId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", required = true, persistent = false, label = "Id de l'activité")
	public Long getAceId() {
		return aceId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id de l'activité'.
	 * @param aceId Long <b>Obligatoire</b>
	 */
	public void setAceId(final Long aceId) {
		this.aceId = aceId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé'. 
	 * @return String label 
	 */
	@Field(domain = "DO_O_LIBELLE", persistent = false, label = "Libellé")
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

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du processus'. 
	 * @return java.util.Date beginTime <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_TIMESTAMP", required = true, persistent = false, label = "Nom du processus")
	public java.util.Date getBeginTime() {
		return beginTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du processus'.
	 * @param beginTime java.util.Date <b>Obligatoire</b>
	 */
	public void setBeginTime(final java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du processus'. 
	 * @return java.util.Date endTime <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_TIMESTAMP", required = true, persistent = false, label = "Nom du processus")
	public java.util.Date getEndTime() {
		return endTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du processus'.
	 * @param endTime java.util.Date <b>Obligatoire</b>
	 */
	public void setEndTime(final java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Durée'. 
	 * @return Long executionTime 
	 */
	@Field(domain = "DO_O_NOMBRE", persistent = false, label = "Durée")
	public Long getExecutionTime() {
		return executionTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Durée'.
	 * @param executionTime Long 
	 */
	public void setExecutionTime(final Long executionTime) {
		this.executionTime = executionTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Statut'. 
	 * @return String status 
	 */
	@Field(domain = "DO_O_CODE_IDENTIFIANT", persistent = false, label = "Statut")
	public String getStatus() {
		return status;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Statut'.
	 * @param status String 
	 */
	public void setStatus(final String status) {
		this.status = status;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
