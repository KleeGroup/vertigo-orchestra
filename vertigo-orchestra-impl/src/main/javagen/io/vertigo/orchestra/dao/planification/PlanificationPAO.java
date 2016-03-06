package io.vertigo.orchestra.dao.planification;

import javax.inject.Inject;

import io.vertigo.app.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.store.StoreServices;

/**
 * PAO : Accès aux objects du package. 
 * PlanificationPAO
 */
public final class PlanificationPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public PlanificationPAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//-----
		this.taskManager = taskManager;
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
	 * Execute la tache TK_RESERVE_PROCESS_TO_EXECUTE.
	 * @param lowerLimit java.util.Date 
	 * @param upperLimit java.util.Date 
	 * @param nodeName String 
	*/
	public void reserveProcessToExecute(final java.util.Date lowerLimit, final java.util.Date upperLimit, final String nodeName) {
		final Task task = createTaskBuilder("TK_RESERVE_PROCESS_TO_EXECUTE")
				.addValue("LOWER_LIMIT", lowerLimit)
				.addValue("UPPER_LIMIT", upperLimit)
				.addValue("NODE_NAME", nodeName)
				.build();
		getTaskManager().execute(task);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
