package io.vertigo.orchestra.domain.execution;

import io.vertigo.dynamo.domain.stereotype.DtDefinition;
import io.vertigo.dynamo.domain.stereotype.Field;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
/**
 * Attention cette classe est générée automatiquement !
 * Objet de données OTaskExecution
 */
@DtDefinition
public final class OTaskExecution implements DtObject {

	/** SerialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long tkeId;
	private java.util.Date beginTime;
	private java.util.Date endTime;
	private String engine;
	private String nodeName;
	private Long tskId;
	private Long preId;
	private String estCd;
	private io.vertigo.orchestra.domain.definition.OTask tache;
	private io.vertigo.orchestra.domain.execution.OProcessExecution processusExecution;
	private io.vertigo.orchestra.domain.referential.OExecutionState executionState;

	/**
	 * Champ : PRIMARY_KEY.
	 * Récupère la valeur de la propriété 'Id de l'execution d'un processus'. 
	 * @return Long tkeId <b>Obligatoire</b>
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "PRIMARY_KEY", notNull = true, label = "Id de l'execution d'un processus")
	public Long getTkeId() {
		return tkeId;
	}

	/**
	 * Champ : PRIMARY_KEY.
	 * Définit la valeur de la propriété 'Id de l'execution d'un processus'.
	 * @param tkeId Long <b>Obligatoire</b>
	 */
	public void setTkeId(final Long tkeId) {
		this.tkeId = tkeId;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date de début'. 
	 * @return java.util.Date beginTime <b>Obligatoire</b>
	 */
	@Field(domain = "DO_TIMESTAMP", notNull = true, label = "Date de début")
	public java.util.Date getBeginTime() {
		return beginTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date de début'.
	 * @param beginTime java.util.Date <b>Obligatoire</b>
	 */
	public void setBeginTime(final java.util.Date beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Date de fin'. 
	 * @return java.util.Date endTime 
	 */
	@Field(domain = "DO_TIMESTAMP", label = "Date de fin")
	public java.util.Date getEndTime() {
		return endTime;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Date de fin'.
	 * @param endTime java.util.Date 
	 */
	public void setEndTime(final java.util.Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * Champ : DATA.
	 * Récupère la valeur de la propriété 'Implémentation effective de l'execution'. 
	 * @return String engine 
	 */
	@Field(domain = "DO_CLASSE", label = "Implémentation effective de l'execution")
	public String getEngine() {
		return engine;
	}

	/**
	 * Champ : DATA.
	 * Définit la valeur de la propriété 'Implémentation effective de l'execution'.
	 * @param engine String 
	 */
	public void setEngine(final String engine) {
		this.engine = engine;
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
	 * Récupère la valeur de la propriété 'Tache'. 
	 * @return Long tskId 
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", label = "Tache")
	public Long getTskId() {
		return tskId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Tache'.
	 * @param tskId Long 
	 */
	public void setTskId(final Long tskId) {
		this.tskId = tskId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'Processus'. 
	 * @return Long preId 
	 */
	@Field(domain = "DO_IDENTIFIANT", type = "FOREIGN_KEY", label = "Processus")
	public Long getPreId() {
		return preId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'Processus'.
	 * @param preId Long 
	 */
	public void setPreId(final Long preId) {
		this.preId = preId;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Récupère la valeur de la propriété 'ExecutionState'. 
	 * @return String estCd 
	 */
	@Field(domain = "DO_CODE_IDENTIFIANT", type = "FOREIGN_KEY", label = "ExecutionState")
	public String getEstCd() {
		return estCd;
	}

	/**
	 * Champ : FOREIGN_KEY.
	 * Définit la valeur de la propriété 'ExecutionState'.
	 * @param estCd String 
	 */
	public void setEstCd(final String estCd) {
		this.estCd = estCd;
	}

	/**
	 * Association : Tache.
	 * @return io.vertigo.orchestra.domain.definition.OTask
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_TSK",
    	fkFieldName = "TSK_ID",
    	primaryDtDefinitionName = "DT_O_TASK",
    	primaryIsNavigable = true,
    	primaryRole = "Tache",
    	primaryLabel = "Tache",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.definition.OTask getTache() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OTask> fkURI = getTacheURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (tache != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OTask> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(tache), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(tache));
			if (!fkURI.toURN().equals(uri.toURN())) {
				tache = null;
			}
		}		
		if (tache == null) {
			tache = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return tache;
	}

	/**
	 * Retourne l'URI: Tache.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_TSK",
    	fkFieldName = "TSK_ID",
    	primaryDtDefinitionName = "DT_O_TASK",
    	primaryIsNavigable = true,
    	primaryRole = "Tache",
    	primaryLabel = "Tache",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.definition.OTask> getTacheURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_TKE_TSK", io.vertigo.orchestra.domain.definition.OTask.class);
	}
	/**
	 * Association : Processus.
	 * @return io.vertigo.orchestra.domain.execution.OProcessExecution
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_PRE",
    	fkFieldName = "PRE_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "ProcessusExecution",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.execution.OProcessExecution getProcessusExecution() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OProcessExecution> fkURI = getProcessusExecutionURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (processusExecution != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OProcessExecution> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(processusExecution), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(processusExecution));
			if (!fkURI.toURN().equals(uri.toURN())) {
				processusExecution = null;
			}
		}		
		if (processusExecution == null) {
			processusExecution = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return processusExecution;
	}

	/**
	 * Retourne l'URI: Processus.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_PRE",
    	fkFieldName = "PRE_ID",
    	primaryDtDefinitionName = "DT_O_PROCESS_EXECUTION",
    	primaryIsNavigable = true,
    	primaryRole = "ProcessusExecution",
    	primaryLabel = "Processus",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.execution.OProcessExecution> getProcessusExecutionURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_TKE_PRE", io.vertigo.orchestra.domain.execution.OProcessExecution.class);
	}

	// Association : ExecutionWorkspace non navigable
	/**
	 * Association : ExecutionState.
	 * @return io.vertigo.orchestra.domain.referential.OExecutionState
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_EST",
    	fkFieldName = "EST_CD",
    	primaryDtDefinitionName = "DT_O_EXECUTION_STATE",
    	primaryIsNavigable = true,
    	primaryRole = "ExecutionState",
    	primaryLabel = "ExecutionState",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.orchestra.domain.referential.OExecutionState getExecutionState() {
		final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OExecutionState> fkURI = getExecutionStateURI();
		if (fkURI == null) {
			return null;
		}
		//On est toujours dans un mode lazy. On s'assure cependant que l'objet associé n'a pas changé
		if (executionState != null) {
			// On s'assure que l'objet correspond à la bonne clé
			final io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OExecutionState> uri;
			uri = new io.vertigo.dynamo.domain.model.URI<>(io.vertigo.dynamo.domain.util.DtObjectUtil.findDtDefinition(executionState), io.vertigo.dynamo.domain.util.DtObjectUtil.getId(executionState));
			if (!fkURI.toURN().equals(uri.toURN())) {
				executionState = null;
			}
		}		
		if (executionState == null) {
			executionState = io.vertigo.core.Home.getComponentSpace().resolve(io.vertigo.dynamo.store.StoreManager.class).getDataStore().get(fkURI);
		}
		return executionState;
	}

	/**
	 * Retourne l'URI: ExecutionState.
	 * @return URI de l'association
	 */
    @io.vertigo.dynamo.domain.stereotype.Association (
    	name = "A_TKE_EST",
    	fkFieldName = "EST_CD",
    	primaryDtDefinitionName = "DT_O_EXECUTION_STATE",
    	primaryIsNavigable = true,
    	primaryRole = "ExecutionState",
    	primaryLabel = "ExecutionState",
    	primaryMultiplicity = "0..1",
    	foreignDtDefinitionName = "DT_O_TASK_EXECUTION",
    	foreignIsNavigable = false,
    	foreignRole = "TaskExecution",
    	foreignLabel = "ExecutionTache",
    	foreignMultiplicity = "0..*"
    )
	public io.vertigo.dynamo.domain.model.URI<io.vertigo.orchestra.domain.referential.OExecutionState> getExecutionStateURI() {
		return io.vertigo.dynamo.domain.util.DtObjectUtil.createURI(this, "A_TKE_EST", io.vertigo.orchestra.domain.referential.OExecutionState.class);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return DtObjectUtil.toString(this);
	}
}
