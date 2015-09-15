package io.vertigo.orchestra.dao.application;

import io.vertigo.core.Home;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.lang.Assertion;

import javax.inject.Inject;

/**
 * PAO : Accès aux objects du package. 
 * ApplicationPAO
 */
public final class ApplicationPAO {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ApplicationPAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//---------------------------------------------------------------------
		this.taskManager = taskManager;
	}

	/**
	 * Création d'une tache.
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private static TaskBuilder createTaskBuilder(final String taskDefinitonName) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(taskDefinitonName, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_DELETE_ALL.
	*/
	public void deleteAll() {

		final Task task = createTaskBuilder("TK_DELETE_ALL")
				.build();
		taskManager.execute(task);
	}
}
