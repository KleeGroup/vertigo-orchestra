package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.model.Entity;
import io.vertigo.dynamo.domain.model.URI;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.util.DtObjectUtil;

/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OActivityWorkspace
 */
@io.vertigo.dynamo.domain.stereotype.DataSpace("orchestra")
public final class OActivityWorkspace implements Entity {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long acwId;
	private Boolean isIn;
	private String workspace;
	private Long aceId;
	private io.vertigo.orchestra.domain.execution.OActivityExecution activityExecution;

	/** {@inheritDoc} */
	@Override
	public URI<OActivityWorkspace> getURI() {
		return DtObjectUtil.createURI(this);
	}

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id de l'execution d'un processus'.
	 * @return Long acwId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "ID", required = true, label = "Id de l'execution d'un processus")
	public Long getAcwId() {
		return acwId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id de l'execution d'un processus'.
	 * @param acwId Long <b>Obligatoire</b>
	 */
	public void setAcwId(final Long acwId) {
		this.acwId = acwId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Workspace in/out'.
	 * @return Boolean isIn <b>Obligatoire</b>
	 */
	@Field(domain = "DO_O_BOOLEEN", required = true, label = "Workspace in/out")
	public Boolean getIsIn() {
		return isIn;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Workspace in/out'.
	 * @param isIn Boolean <b>Obligatoire</b>
	 */
	public void setIsIn(final Boolean isIn) {
		this.isIn = isIn;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Contenu du workspace'.
	 * @return String workspace
	 */
	@Field(domain = "DO_O_JSON_TEXT", label = "Contenu du workspace")
	public String getWorkspace() {
		return workspace;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Contenu du workspace'.
	 * @param workspace String
	 */
	public void setWorkspace(final String workspace) {
		this.workspace = workspace;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'ActivityExecution'.
	 * @return Long aceId
	 */
	@Field(domain = "DO_O_IDENTIFIANT", type = "FOREIGN_KEY", label = "ActivityExecution")
	public Long getAceId() {
		return aceId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'ActivityExecution'.
	 * @param aceId Long
	 */
	public void setAceId(final Long aceId) {
		this.aceId = aceId;
	}

	/**
	 * Association : ActivityExecution.
	 * @return io.vertigo.orchestra.domain.execution.OActivityExecution
	 */
	public io.vertigo.orchestra.domain.execution.OActivityExecution getActivityExecution() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OActivityExecution> fkURI = getActivityExecutionURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (activityExecution == null || !fkURI.equals(activityExecution.getURI())) {
			activityExecution = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().readOne(fkURI);
		}
		return activityExecution;
	}

	/**
	 * Retourne l'URI: ActivityExecution.
	 * @return URI de l'association
	 */
	@io.vertigo.dynamo.domain.stereotype.Association(
			name = "A_TKW_TKE",
			fkFieldName = "ACE_ID",
			primaryDtDefinitionName = "DT_O_ACTIVITY_EXECUTION",
			primaryIsNavigable = true,
			primaryRole = "ActivityExecution",
			primaryLabel = "ActivityExecution",
			primaryMultiplicity = "0..1",
			foreignDtDefinitionName = "DT_O_ACTIVITY_WORKSPACE",
			foreignIsNavigable = false,
			foreignRole = "ActivityWorkspace",
			foreignLabel = "ActivityWorkspace",
			foreignMultiplicity = "0..*")
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OActivityExecution> getActivityExecutionURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_TKW_TKE", io.vertigo.orchestra.domain.execution.OActivityExecution.class);
	}


	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
