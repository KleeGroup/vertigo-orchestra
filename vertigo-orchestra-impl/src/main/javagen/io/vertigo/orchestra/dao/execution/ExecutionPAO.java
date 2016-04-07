package io.vertigo.orchestra.dao.execution;

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
 * ExecutionPAO
 */
public final class ExecutionPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ExecutionPAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_RESERVE_ACTIVITIES_TO_LAUNCH.
	 * @param nodId Long 
	 * @param maxNumber Long 
	*/
	public void reserveActivitiesToLaunch(final Long nodId, final Long maxNumber) {
		final Task task = createTaskBuilder("TK_RESERVE_ACTIVITIES_TO_LAUNCH")
				.addValue("NOD_ID", nodId)
				.addValue("MAX_NUMBER", maxNumber)
				.build();
		getTaskManager().execute(task);
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
