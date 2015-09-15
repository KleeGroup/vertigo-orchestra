package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;

import io.vertigo.core.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.task.model.TaskResult;

/**
 * PAO : Accès aux objects du package. 
 * ExecutionPAO
 */
public final class ExecutionPAO {
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_RESERVE_TASKS_TO_LAUNCH */
		TK_RESERVE_TASKS_TO_LAUNCH,
	}

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
	private TaskBuilder createTaskBuilder(final Tasks task) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(task.toString(), TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_RESERVE_TASKS_TO_LAUNCH.
	*/
	public void reserveTasksToLaunch() {
		final Task task = createTaskBuilder(Tasks.TK_RESERVE_TASKS_TO_LAUNCH)
				.build();
		getTaskManager().execute(task);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
