package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OExecutionWorkspace
 */
@DtDefinition
public final class OExecutionWorkspace implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long exwId;
	private Boolean isIn;
	private String workspace;
	private Long tkeId;
	private io.vertigo.orchestra.domain.execution.OTaskExecution taskExecution;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'Id de l'execution d'un processus'. 
	 * @return Long exwId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "Id de l'execution d'un processus")
	public Long getExwId() {
		return exwId;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'Id de l'execution d'un processus'.
	 * @param exwId Long <b>Obligatoire</b>
	 */
	public void setExwId(final Long exwId) {
		this.exwId = exwId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Workspace in/out'. 
	 * @return Boolean isIn <b>Obligatoire</b>
	 */
	@Field(domain = "DO_BOOLEEN", notNull = true, label = "Workspace in/out")
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
	@Field(domain = "DO_JSON_TEXT", label = "Contenu du workspace")
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
	 * Récupère la valeur de la propriété 'TaskExecution'. 
	 * @return Long tkeId 
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", label = "TaskExecution")
	public Long getTkeId() {
		return tkeId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'TaskExecution'.
	 * @param tkeId Long 
	 */
	public void setTkeId(final Long tkeId) {
		this.tkeId = tkeId;
	}

	/**
	 * Association : TaskExecution.
	 * @return io.vertigo.orchestra.domain.execution.OTaskExecution
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_EXW_TKE",
    	fkFieldName = "TKE_ID",
    	primaryDtDefinitionName = "DT_O_TASK_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "TaskExecution",
    	primaryLabel = "TaskExecution",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_EXECUTION_WORKSPACE",
    	foreignIsNavigable = false,
    	foreignRole = "ExecutionWorkspace",
    	foreignLabel = "ExecutionWorkspace",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.execution.OTaskExecution getTaskExecution() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OTaskExecution> fkURI = getTaskExecutionURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (taskExecution != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OTaskExecution> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(taskExecution), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(taskExecution));
			if (!fkURI.toURN().equals(uri.toURN())) {
				taskExecution = null;
			}
		}		
		if (taskExecution == null) {
			taskExecution = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return taskExecution;
	}

	/**
	 * Retourne l'URI: TaskExecution.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_EXW_TKE",
    	fkFieldName = "TKE_ID",
    	primaryDtDefinitionName = "DT_O_TASK_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "TaskExecution",
    	primaryLabel = "TaskExecution",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_EXECUTION_WORKSPACE",
    	foreignIsNavigable = false,
    	foreignRole = "ExecutionWorkspace",
    	foreignLabel = "ExecutionWorkspace",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OTaskExecution> getTaskExecutionURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_EXW_TKE", io.vertigo.orchestra.domain.execution.OTaskExecution.class);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
