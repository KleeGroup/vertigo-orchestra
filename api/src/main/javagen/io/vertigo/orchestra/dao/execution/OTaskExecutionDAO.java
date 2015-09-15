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
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_GET_TASKS_TO_LAUNCH */
		TK_GET_TASKS_TO_LAUNCH,
	}

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
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private TaskBuilder createTaskBuilder(final Tasks task) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(task.toString(), TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_TASKS_TO_LAUNCH.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> dtcTaskExecution
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.execution.OTaskExecution> getTasksToLaunch() {
		final Task task = createTaskBuilder(Tasks.TK_GET_TASKS_TO_LAUNCH)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
