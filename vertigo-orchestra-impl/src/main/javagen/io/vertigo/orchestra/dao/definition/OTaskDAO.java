package io.vertigo.orchestra.dao.definition;

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
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskDAO
 */
public final class OTaskDAO extends DAOBroker<OTask, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OTaskDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OTask.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_FIRST_TASK_BY_PROCESS.
	 * @param proId Long 
	 * @return io.vertigo.orchestra.domain.definition.OTask dtOTask
	*/
	public io.vertigo.orchestra.domain.definition.OTask getFirstTaskByProcess(final Long proId) {
		final Task task = createTaskBuilder("TK_GET_FIRST_TASK_BY_PROCESS")
				.addValue("PRO_ID", proId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_NEXT_TASK_BY_TSK_ID.
	 * @param tskId Long 
	 * @return Option de io.vertigo.orchestra.domain.definition.OTask dtOTask
	*/
	public Option<io.vertigo.orchestra.domain.definition.OTask> getNextTaskByTskId(final Long tskId) {
		final Task task = createTaskBuilder("TK_GET_NEXT_TASK_BY_TSK_ID")
				.addValue("TSK_ID", tskId)
				.build();
		return Option.option((io.vertigo.orchestra.domain.definition.OTask)getTaskManager()
				.execute(task)
				.getResult());
	}


}
