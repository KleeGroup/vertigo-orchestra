package io.vertigo.orchestra.webapi.domain.summary;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OExecutionSummary
 */
@DtDefinition(persistent = false)
public final class OExecutionSummary implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long proId;
	private String processName;
	private String processLabel;
	private java.util.Date lastExecutionTime;
	private java.util.Date nextExecutionTime;
	private Long errorsCount;
	private Long misfiredCount;
	private Long successfulCount;
	private Long averageExecutionTime;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id du processus'. 
	 * @return Long proId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", required = true, persistent = false, label = "Id du processus")
	public Long getProId() {
		return proId;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Id du processus'.
	 * @param proId Long <b>Obligatoire</b>
	 */
	public void setProId(final Long proId) {
		this.proId = proId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du processus'. 
	 * @return String processName <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, persistent = false, label = "Nom du processus")
	public String getProcessName() {
		return processName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du processus'.
	 * @param processName String <b>Obligatoire</b>
	 */
	public void setProcessName(final String processName) {
		this.processName = processName;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du processus'. 
	 * @return String processLabel <b>Obligatoire</b>
	 */
	@Field(domain = "DO_LIBELLE", required = true, persistent = false, label = "Nom du processus")
	public String getProcessLabel() {
		return processLabel;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du processus'.
	 * @param processLabel String <b>Obligatoire</b>
	 */
	public void setProcessLabel(final String processLabel) {
		this.processLabel = processLabel;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Dernière exécution le'. 
	 * @return java.util.Date lastExecutionTime 
	 */
	@Field(domain = "DO_TIMESTAMP", persistent = false, label = "Dernière exécution le")
	public java.util.Date getLastExecutionTime() {
		return lastExecutionTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Dernière exécution le'.
	 * @param lastExecutionTime java.util.Date 
	 */
	public void setLastExecutionTime(final java.util.Date lastExecutionTime) {
		this.lastExecutionTime = lastExecutionTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Prochaine exécution le'. 
	 * @return java.util.Date nextExecutionTime 
	 */
	@Field(domain = "DO_TIMESTAMP", persistent = false, label = "Prochaine exécution le")
	public java.util.Date getNextExecutionTime() {
		return nextExecutionTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Prochaine exécution le'.
	 * @param nextExecutionTime java.util.Date 
	 */
	public void setNextExecutionTime(final java.util.Date nextExecutionTime) {
		this.nextExecutionTime = nextExecutionTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du noeud'. 
	 * @return Long errorsCount 
	 */
	@Field(domain = "DO_NOMBRE", persistent = false, label = "Nom du noeud")
	public Long getErrorsCount() {
		return errorsCount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du noeud'.
	 * @param errorsCount Long 
	 */
	public void setErrorsCount(final Long errorsCount) {
		this.errorsCount = errorsCount;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du noeud'. 
	 * @return Long misfiredCount 
	 */
	@Field(domain = "DO_NOMBRE", persistent = false, label = "Nom du noeud")
	public Long getMisfiredCount() {
		return misfiredCount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du noeud'.
	 * @param misfiredCount Long 
	 */
	public void setMisfiredCount(final Long misfiredCount) {
		this.misfiredCount = misfiredCount;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom du noeud'. 
	 * @return Long successfulCount 
	 */
	@Field(domain = "DO_NOMBRE", persistent = false, label = "Nom du noeud")
	public Long getSuccessfulCount() {
		return successfulCount;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du noeud'.
	 * @param successfulCount Long 
	 */
	public void setSuccessfulCount(final Long successfulCount) {
		this.successfulCount = successfulCount;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Durée moyenne d'exécution'. 
	 * @return Long averageExecutionTime 
	 */
	@Field(domain = "DO_NOMBRE", persistent = false, label = "Durée moyenne d'exécution")
	public Long getAverageExecutionTime() {
		return averageExecutionTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Durée moyenne d'exécution'.
	 * @param averageExecutionTime Long 
	 */
	public void setAverageExecutionTime(final Long averageExecutionTime) {
		this.averageExecutionTime = averageExecutionTime;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
