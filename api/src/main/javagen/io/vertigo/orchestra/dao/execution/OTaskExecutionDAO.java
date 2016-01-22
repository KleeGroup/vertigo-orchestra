package io.vertigo.orchestra.dao.execution;

import io.vertigo.core.Home;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.orchestra.domain.execution.OTaskExecution;

import javax.inject.Inject;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskExecutionDAO
 */
public final class OTaskExecutionDAO extends DAOBroker<OTaskExecution, java.lang.Long> {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OTaskExecutionDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OTaskExecution.class, storeManager, taskManager);
	}

	/**
	 * Création d'une tache.
	 * @param name the task name
	 * @return Builder de la tache
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_TASKS_TO_LAUNCH.
	 * @param nodeName String 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> dtcTaskExecution
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> getTasksToLaunch(final String nodeName) {
		final Task task = createTaskBuilder("TK_GET_TASKS_TO_LAUNCH")
				.addValue("NODE_NAME", nodeName)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_TASK_EXECUTIONS_BY_PRE_ID.
	 * @param preId Long 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> dtcOTaskExecution
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> getTaskExecutionsByPreId(final Long preId) {
		final Task task = createTaskBuilder("TK_GET_TASK_EXECUTIONS_BY_PRE_ID")
				.addValue("PRE_ID", preId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
