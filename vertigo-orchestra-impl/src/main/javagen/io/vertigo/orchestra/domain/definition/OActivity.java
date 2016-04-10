package io.vertigo.orchestra.domain.definition;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OActivity
 */
@DtDefinition
public final class OActivity implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long actId;
	private String name;
	private String label;
	private Long number;
	private Boolean milestone;
	private String engine;
	private Long proId;
	private io.vertigo.orchestra.domain.definition.OProcess process;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id Activité'. 
	 * @return Long actId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "Id Activité")
	public Long getActId() {
		return actId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id Activité'.
	 * @param actId Long <b>Obligatoire</b>
	 */
	public void setActId(final Long actId) {
		this.actId = actId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Nom de l'activité'. 
	 * @return String name 
	 */
	@Field(domain = "DO_LIBELLE", label = "Nom de l'activité")
	public String getName() {
		return name;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom de l'activité'.
	 * @param name String 
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Libellé de l'activité'. 
	 * @return String label 
	 */
	@Field(domain = "DO_LIBELLE", label = "Libellé de l'activité")
	public String getLabel() {
		return label;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Libellé de l'activité'.
	 * @param label String 
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Numéro de l'activité'. 
	 * @return Long number 
	 */
	@Field(domain = "DO_NOMBRE", label = "Numéro de l'activité")
	public Long getNumber() {
		return number;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Numéro de l'activité'.
	 * @param number Long 
	 */
	public void setNumber(final Long number) {
		this.number = number;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Jalon'. 
	 * @return Boolean milestone 
	 */
	@Field(domain = "DO_BOOLEEN", label = "Jalon")
	public Boolean getMilestone() {
		return milestone;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Jalon'.
	 * @param milestone Boolean 
	 */
	public void setMilestone(final Boolean milestone) {
		this.milestone = milestone;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Implémentation de l'activité'. 
	 * @return String engine 
	 */
	@Field(domain = "DO_CLASSE", label = "Implémentation de l'activité")
	public String getEngine() {
		return engine;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Implémentation de l'activité'.
	 * @param engine String 
	 */
	public void setEngine(final String engine) {
		this.engine = engine;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Processus'. 
	 * @return Long proId 
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", label = "Processus")
	public Long getProId() {
		return proId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Processus'.
	 * @param proId Long 
	 */
	public void setProId(final Long proId) {
		this.proId = proId;
	}

	/**
	 * Association : Processus.
	 * @return io.vertigo.orchestra.domain.definition.OProcess
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_ACT_PRO",
    	fkFieldName = "PRO_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS",
    	primaryIsNavigable = true,
    	primaryRole = "Process",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_ACTIVITY",
    	foreignIsNavigable = false,
    	foreignRole = "Activity",
    	foreignLabel = "Activity",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.definition.OProcess getProcess() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> fkURI = getProcessURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (process != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(process), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(process));
			if (!fkURI.toURN().equals(uri.toURN())) {
				process = null;
			}
		}		
		if (process == null) {
			process = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return process;
	}

	/**
	 * Retourne l'URI: Processus.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_ACT_PRO",
    	fkFieldName = "PRO_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS",
    	primaryIsNavigable = true,
    	primaryRole = "Process",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_ACTIVITY",
    	foreignIsNavigable = false,
    	foreignRole = "Activity",
    	foreignLabel = "Activity",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> getProcessURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_ACT_PRO", io.vertigo.orchestra.domain.definition.OProcess.class);
	}

	// Association : ExecutionActivity non navigable

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}