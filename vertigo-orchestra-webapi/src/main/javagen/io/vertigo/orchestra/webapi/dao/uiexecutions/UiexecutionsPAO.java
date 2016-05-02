package io.vertigo.orchestra.webapi.dao.uiexecutions;

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
 * UiexecutionsPAO
 */
public final class UiexecutionsPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public UiexecutionsPAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_GET_EXECUTION_BY_PRE_ID.
	 * @param preId Long 
	 * @return io.vertigo.orchestra.webapi.domain.summary.OProcessExecutionUi dtOProcessExecutionUi
	*/
	public io.vertigo.orchestra.webapi.domain.summary.OProcessExecutionUi getExecutionByPreId(final Long preId) {
		final Task task = createTaskBuilder("TK_GET_EXECUTION_BY_PRE_ID")
				.addValue("PRE_ID", preId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_EXECUTIONS_BY_PROCESS_NAME.
	 * @param name String 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.webapi.domain.summary.OProcessExecutionUi> dtcOProcessExecutionUi
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.webapi.domain.summary.OProcessExecutionUi> getExecutionsByProcessName(final String name) {
		final Task task = createTaskBuilder("TK_GET_EXECUTIONS_BY_PROCESS_NAME")
				.addValue("NAME", name)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_ACTIVITIY_BY_ACE_ID.
	 * @param aceId Long 
	 * @return io.vertigo.orchestra.webapi.domain.summary.OActivityExecutionUi dtOActivityExecutionUi
	*/
	public io.vertigo.orchestra.webapi.domain.summary.OActivityExecutionUi getActivitiyByAceId(final Long aceId) {
		final Task task = createTaskBuilder("TK_GET_ACTIVITIY_BY_ACE_ID")
				.addValue("ACE_ID", aceId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_ACTIVITIES_BY_PRE_ID.
	 * @param preId Long 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.webapi.domain.summary.OActivityExecutionUi> dtcOActivityExecutionUi
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.webapi.domain.summary.OActivityExecutionUi> getActivitiesByPreId(final Long preId) {
		final Task task = createTaskBuilder("TK_GET_ACTIVITIES_BY_PRE_ID")
				.addValue("PRE_ID", preId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

    
    private TaskManager getTaskManager(){
    	return taskManager;
    } 
}
