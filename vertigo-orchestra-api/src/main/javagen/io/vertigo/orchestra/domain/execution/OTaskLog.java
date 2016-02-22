package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OTaskLog
 */
@DtDefinition
public final class OTaskLog implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long tklId;
	private String log;
	private Long tkeId;
	private io.vertigo.orchestra.domain.execution.OTaskExecution taskExecution;

	/**
	 * Champ : ID.
	 * Récupère la valeur de la propriété 'Id du log'. 
	 * @return Long tklId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "ID", required = true, label = "Id du log")
	public Long getTklId() {
		return tklId;
	}

	/**
	 * Champ : ID.
	 * Définit la valeur de la propriété 'Id du log'.
	 * @param tklId Long <b>Obligatoire</b>
	 */
	public void setTklId(final Long tklId) {
		this.tklId = tklId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Contenu du log'. 
	 * @return String log 
	 */
	@Field(domain = "DO_TEXT", label = "Contenu du log")
	public String getLog() {
		return log;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Contenu du log'.
	 * @param log String 
	 */
	public void setLog(final String log) {
		this.log = log;
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
    	name = "A_TKL_TKE",
    	fkFieldName = "TKE_ID",
    	primaryDtDefinitionName = "DT_O_TASK_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "TaskExecution",
    	primaryLabel = "TaskExecution",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_LOG",
    	foreignIsNavigable = false,
    	foreignRole = "TaskLog",
    	foreignLabel = "TaskLog",
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
			taskExecution = io.vertigo.app.Home.getApp().getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return taskExecution;
	}

	/**
	 * Retourne l'URI: TaskExecution.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKL_TKE",
    	fkFieldName = "TKE_ID",
    	primaryDtDefinitionName = "DT_O_TASK_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "TaskExecution",
    	primaryLabel = "TaskExecution",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_LOG",
    	foreignIsNavigable = false,
    	foreignRole = "TaskLog",
    	foreignLabel = "TaskLog",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OTaskExecution> getTaskExecutionURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_TKL_TKE", io.vertigo.orchestra.domain.execution.OTaskExecution.class);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
