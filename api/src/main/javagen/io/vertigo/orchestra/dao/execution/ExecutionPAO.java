package io.vertigo.orchestra.dao.execution;

import io.vertigo.core.Home;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * PAO : Accès aux objects du package. 
 * ExecutionPAO
 */
public final class ExecutionPAO {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ExecutionPAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//---------------------------------------------------------------------
		this.taskManager = taskManager;
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
	 * Execute la tache TK_RESERVE_TASKS_TO_LAUNCH.
	*/
	public void reserveTasksToLaunch() {
		final Task task = createTaskBuilder("TK_RESERVE_TASKS_TO_LAUNCH")
				.build();
		taskManager.execute(task);
	}
}
