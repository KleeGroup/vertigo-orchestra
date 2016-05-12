package io.vertigo.orchestra.webapi.domain.uiexecutions;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OProcessExecutionUi
 */
@DtDefinition(persistent = false)
public final class OProcessExecutionUi implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long preId;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private Long executionTime;
	private String status;
	private Boolean checked;
	private java.util.Date checkingDate;
	private String checkingComment;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id de l'activité'. 
	 * @return Long preId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", required = true, persistent = false, label = "Id de l'activité")
	public Long getPreId() {
		return preId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id de l'activité'.
	 * @param preId Long <b>Obligatoire</b>
	 */
	public void setPreId(final Long preId) {
		this.preId = preId;
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

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Pris en charge'. 
	 * @return Boolean checked 
	 */
	@Field(domain = "DO_O_BOOLEEN", persistent = false, label = "Pris en charge")
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Pris en charge'.
	 * @param checked Boolean 
	 */
	public void setChecked(final Boolean checked) {
		this.checked = checked;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date de prise en charge'. 
	 * @return java.util.Date checkingDate 
	 */
	@Field(domain = "DO_O_TIMESTAMP", persistent = false, label = "Date de prise en charge")
	public java.util.Date getCheckingDate() {
		return checkingDate;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date de prise en charge'.
	 * @param checkingDate java.util.Date 
	 */
	public void setCheckingDate(final java.util.Date checkingDate) {
		this.checkingDate = checkingDate;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Commentaire'. 
	 * @return String checkingComment 
	 */
	@Field(domain = "DO_O_TEXT", persistent = false, label = "Commentaire")
	public String getCheckingComment() {
		return checkingComment;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Commentaire'.
	 * @param checkingComment String 
	 */
	public void setCheckingComment(final String checkingComment) {
		this.checkingComment = checkingComment;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
