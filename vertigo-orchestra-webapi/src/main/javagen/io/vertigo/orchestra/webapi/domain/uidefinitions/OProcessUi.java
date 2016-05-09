package io.vertigo.orchestra.webapi.domain.uidefinitions;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OProcessUi
 */
@DtDefinition(persistent = false)
public final class OProcessUi implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long proId;
	private String name;
	private String label;
	private String cronExpression;
	private String initialParams;
	private Boolean multiexecution;
	private Boolean active;
	private Long rescuePeriod;
	private String metadatas;

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Id du processus'. 
	 * @return Long proId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", required = true, persistent = false, label = "Id du processus")
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
	 * @return String name 
	 */
	@Field(domain = "DO_O_LIBELLE", persistent = false, label = "Nom du processus")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du processus'.
	 * @param name String 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé du processus'. 
	 * @return String label 
	 */
	@Field(domain = "DO_O_LIBELLE", persistent = false, label = "Libellé du processus")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé du processus'.
	 * @param label String 
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Expression récurrence du processus'. 
	 * @return String cronExpression 
	 */
	@Field(domain = "DO_O_LIBELLE", persistent = false, label = "Expression récurrence du processus")
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Expression récurrence du processus'.
	 * @param cronExpression String 
	 */
	public void setCronExpression(final String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Paramètres initiaux sous forme de JSON'. 
	 * @return String initialParams 
	 */
	@Field(domain = "DO_O_JSON_TEXT", persistent = false, label = "Paramètres initiaux sous forme de JSON")
	public String getInitialParams() {
		return initialParams;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Paramètres initiaux sous forme de JSON'.
	 * @param initialParams String 
	 */
	public void setInitialParams(final String initialParams) {
		this.initialParams = initialParams;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Accepte la multi-execution'. 
	 * @return Boolean multiexecution 
	 */
	@Field(domain = "DO_O_BOOLEEN", persistent = false, label = "Accepte la multi-execution")
	public Boolean getMultiexecution() {
		return multiexecution;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Accepte la multi-execution'.
	 * @param multiexecution Boolean 
	 */
	public void setMultiexecution(final Boolean multiexecution) {
		this.multiexecution = multiexecution;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Processus actif'. 
	 * @return Boolean active <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_BOOLEEN", required = true, persistent = false, label = "Processus actif")
	public Boolean getActive() {
		return active;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Processus actif'.
	 * @param active Boolean <b>Obligatoire</b>
	 */
	public void setActive(final Boolean active) {
		this.active = active;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Temps de validité d'une planification'. 
	 * @return Long rescuePeriod <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_NOMBRE", required = true, persistent = false, label = "Temps de validité d'une planification")
	public Long getRescuePeriod() {
		return rescuePeriod;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Temps de validité d'une planification'.
	 * @param rescuePeriod Long <b>Obligatoire</b>
	 */
	public void setRescuePeriod(final Long rescuePeriod) {
		this.rescuePeriod = rescuePeriod;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Métadonnées du processus'. 
	 * @return String metadatas 
	 */
	@Field(domain = "DO_O_METADATAS", persistent = false, label = "Métadonnées du processus")
	public String getMetadatas() {
		return metadatas;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Métadonnées du processus'.
	 * @param metadatas String 
	 */
	public void setMetadatas(final String metadatas) {
		this.metadatas = metadatas;
	}

	//Aucune Association déclarée

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
