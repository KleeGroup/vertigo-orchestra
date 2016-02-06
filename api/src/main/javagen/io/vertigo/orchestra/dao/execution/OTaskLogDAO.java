package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import io.vertigo.app.Home;
import io.vertigo.lang.Option;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OTaskLog;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskLogDAO
 */
public final class OTaskLogDAO extends DAOBroker<OTaskLog, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OTaskLogDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OTaskLog.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_TASK_LOG_BY_TKE_ID.
	 * @param tkeId Long 
	 * @return Option de io.vertigo.orchestra.domain.execution.OTaskLog dtcOTaskLog
	*/
	public Option<io.vertigo.orchestra.domain.execution.OTaskLog> getTaskLogByTkeId(final Long tkeId) {
		final Task task = createTaskBuilder("TK_GET_TASK_LOG_BY_TKE_ID")
				.addValue("TKE_ID", tkeId)
				.build();
		return Option.option((io.vertigo.orchestra.domain.execution.OTaskLog)getTaskManager()
				.execute(task)
				.getResult());
	}


}
