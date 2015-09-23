package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import io.vertigo.core.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OExecutionWorkspace;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OExecutionWorkspaceDAO
 */
public final class OExecutionWorkspaceDAO extends DAOBroker<OExecutionWorkspace, java.lang.Long> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OExecutionWorkspaceDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OExecutionWorkspace.class, storeManager, taskManager);
	}
	

	/**
	 * Création d'une tache.
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_EXECUTION_WORKSPACE.
	 * @param tkeId Long 
	 * @param in Boolean 
	 * @return io.vertigo.orchestra.domain.execution.OExecutionWorkspace dtOExecutionWorkspace
	*/
	public io.vertigo.orchestra.domain.execution.OExecutionWorkspace getExecutionWorkspace(final Long tkeId, final Boolean in) {
		final Task task = createTaskBuilder("TK_GET_EXECUTION_WORKSPACE")
				.addValue("TKE_ID", tkeId)
				.addValue("IN", in)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
