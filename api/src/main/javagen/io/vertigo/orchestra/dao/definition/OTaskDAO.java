package io.vertigo.orchestra.dao.definition;

import io.vertigo.core.Home;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.orchestra.domain.definition.OTask;

import javax.inject.Inject;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskDAO
 */
public final class OTaskDAO extends DAOBroker<OTask, java.lang.Long> {
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
	 * Création d'une tache.
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private static TaskBuilder createTaskBuilder(final String taskDefinitionName) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(taskDefinitionName, TaskDefinition.class);
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

}
