package io.vertigo.orchestra.dao.planification;

import io.vertigo.core.Home;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * PAO : Accès aux objects du package. 
 * PlanificationPAO
 */
public final class PlanificationPAO {
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_RESERVE_PROCESS_TO_EXECUTE */
		TK_RESERVE_PROCESS_TO_EXECUTE,
	}

	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public PlanificationPAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_RESERVE_PROCESS_TO_EXECUTE.
	*/
	public void reserveProcessToExecute() {
		final Task task = createTaskBuilder(Tasks.TK_RESERVE_PROCESS_TO_EXECUTE)
				.build();
		getTaskManager().execute(task);
	}

	private TaskManager getTaskManager() {
		return taskManager;
	}
}
