package io.vertigo.orchestra.domain.planification;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OProcessPlanification
 */
@DtDefinition
public final class OProcessPlanification implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long prpId;
	private java.util.Date expectedTime;
	private String state;
	private String initialParams;
	private String nodeName;
	private Long proId;
	private String pstCd;
	private io.vertigo.orchestra.domain.definition.OProcess processus;
	private io.vertigo.orchestra.domain.referential.OPlanificationState planificationState;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'Id Planification'. 
	 * @return Long prpId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "Id Planification")
	public Long getPrpId() {
		return prpId;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'Id Planification'.
	 * @param prpId Long <b>Obligatoire</b>
	 */
	public void setPrpId(final Long prpId) {
		this.prpId = prpId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date d'execution prévue'. 
	 * @return java.util.Date expectedTime 
	 */
	@Field(domain = "DO_TIMESTAMP", label = "Date d'execution prévue")
	public java.util.Date getExpectedTime() {
		return expectedTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date d'execution prévue'.
	 * @param expectedTime java.util.Date 
	 */
	public void setExpectedTime(final java.util.Date expectedTime) {
		this.expectedTime = expectedTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Etat de la planification'. 
	 * @return String state 
	 */
	@Field(domain = "DO_CODE_IDENTIFIANT", label = "Etat de la planification")
	public String getState() {
		return state;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Etat de la planification'.
	 * @param state String 
	 */
	public void setState(final String state) {
		this.state = state;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Paramètres initiaux sous forme de JSON'. 
	 * @return String initialParams 
	 */
	@Field(domain = "DO_JSON_TEXT", label = "Paramètres initiaux sous forme de JSON")
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
	 * Récupère la valeur de la propriété 'Nom du noeud'. 
	 * @return String nodeName 
	 */
	@Field(domain = "DO_LIBELLE", label = "Nom du noeud")
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Nom du noeud'.
	 * @param nodeName String 
	 */
	public void setNodeName(final String nodeName) {
		this.nodeName = nodeName;
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
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'PlanificationState'. 
	 * @return String pstCd 
	 */
	@Field(domain = "DO_CODE_IDENTIFIANT", type = "FOREIGN_KEY", label = "PlanificationState")
	public String getPstCd() {
		return pstCd;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'PlanificationState'.
	 * @param pstCd String 
	 */
	public void setPstCd(final String pstCd) {
		this.pstCd = pstCd;
	}

	/**
	 * Association : Processus.
	 * @return io.vertigo.orchestra.domain.definition.OProcess
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_PRP_PRO",
    	fkFieldName = "PRO_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS",
    	primaryIsNavigable = true,
    	primaryRole = "Processus",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_PROCESS_PLANIFICATION",
    	foreignIsNavigable = false,
    	foreignRole = "ProcessPlanification",
    	foreignLabel = "PlanificationProcessus",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.definition.OProcess getProcessus() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> fkURI = getProcessusURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (processus != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(processus), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(processus));
			if (!fkURI.toURN().equals(uri.toURN())) {
				processus = null;
			}
		}		
		if (processus == null) {
			processus = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return processus;
	}

	/**
	 * Retourne l'URI: Processus.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_PRP_PRO",
    	fkFieldName = "PRO_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS",
    	primaryIsNavigable = true,
    	primaryRole = "Processus",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_PROCESS_PLANIFICATION",
    	foreignIsNavigable = false,
    	foreignRole = "ProcessPlanification",
    	foreignLabel = "PlanificationProcessus",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OProcess> getProcessusURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_PRP_PRO", io.vertigo.orchestra.domain.definition.OProcess.class);
	}
	/**
	 * Association : PlanificationState.
	 * @return io.vertigo.orchestra.domain.referential.OPlanificationState
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_PRP_PST",
    	fkFieldName = "PST_CD",
    	primaryDtDefinitionName = "DT_O_PLANIFICATION_STATE",
    	primaryIsNavigable = true,
    	primaryRole = "PlanificationState",
    	primaryLabel = "PlanificationState",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_PROCESS_PLANIFICATION",
    	foreignIsNavigable = false,
    	foreignRole = "ProcessPlanification",
    	foreignLabel = "ProcessPlanification",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.referential.OPlanificationState getPlanificationState() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OPlanificationState> fkURI = getPlanificationStateURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (planificationState != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OPlanificationState> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(planificationState), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(planificationState));
			if (!fkURI.toURN().equals(uri.toURN())) {
				planificationState = null;
			}
		}		
		if (planificationState == null) {
			planificationState = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return planificationState;
	}

	/**
	 * Retourne l'URI: PlanificationState.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_PRP_PST",
    	fkFieldName = "PST_CD",
    	primaryDtDefinitionName = "DT_O_PLANIFICATION_STATE",
    	primaryIsNavigable = true,
    	primaryRole = "PlanificationState",
    	primaryLabel = "PlanificationState",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_PROCESS_PLANIFICATION",
    	foreignIsNavigable = false,
    	foreignRole = "ProcessPlanification",
    	foreignLabel = "ProcessPlanification",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OPlanificationState> getPlanificationStateURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_PRP_PST", io.vertigo.orchestra.domain.referential.OPlanificationState.class);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
