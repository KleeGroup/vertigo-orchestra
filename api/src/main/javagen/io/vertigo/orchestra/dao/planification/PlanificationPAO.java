package io.vertigo.orchestra.dao.planification;

import javax.inject.Inject;

import io.vertigo.core.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;

/**
 * PAO : Accès aux objects du package. 
 * PlanificationPAO
 */
public final class PlanificationPAO {
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
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_RESERVE_PROCESS_TO_EXECUTE.
	 * @param lowerLimit java.util.Date 
	 * @param upperLimit java.util.Date 
	*/
	public void reserveProcessToExecute(final java.util.Date lowerLimit, final java.util.Date upperLimit) {
		final Task task = createTaskBuilder("TK_RESERVE_PROCESS_TO_EXECUTE")
				.addValue("LOWER_LIMIT", lowerLimit)
				.addValue("UPPER_LIMIT", upperLimit)
				.build();
		getTaskManager().execute(task);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
