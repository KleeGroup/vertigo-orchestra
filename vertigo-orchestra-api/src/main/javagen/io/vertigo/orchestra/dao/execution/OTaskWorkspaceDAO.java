package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OTaskWorkspace;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskWorkspaceDAO
 */
public final class OTaskWorkspaceDAO extends DAOBroker<OTaskWorkspace, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OTaskWorkspaceDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OTaskWorkspace.class, storeManager, taskManager);
	}
	

	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getApp().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_TASK_WORKSPACE.
	 * @param tkeId Long 
	 * @param in Boolean 
	 * @return io.vertigo.orchestra.domain.execution.OTaskWorkspace dtOTaskWorkspace
	*/
	public io.vertigo.orchestra.domain.execution.OTaskWorkspace getTaskWorkspace(final Long tkeId, final Boolean in) {
		final Task task = createTaskBuilder("TK_GET_TASK_WORKSPACE")
				.addValue("TKE_ID", tkeId)
				.addValue("IN", in)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
