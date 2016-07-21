package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import java.util.Optional;
import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OActivityLog;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OActivityLogDAO
 */
public final class OActivityLogDAO extends DAO<OActivityLog, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OActivityLogDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OActivityLog.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_LOG_BY_PRE_ID.
	 * @param preId Long 
	 * @return Option de io.vertigo.orchestra.domain.execution.OActivityLog dtActivityLog
	*/
	public Optional<io.vertigo.orchestra.domain.execution.OActivityLog> getLogByPreId(final Long preId) {
		final Task task = createTaskBuilder("TK_GET_LOG_BY_PRE_ID")
				.addValue("PRE_ID", preId)
				.build();
		return Optional.ofNullable((io.vertigo.orchestra.domain.execution.OActivityLog)getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TK_GET_ACTIVITY_LOG_BY_ACE_ID.
	 * @param aceId Long 
	 * @return Option de io.vertigo.orchestra.domain.execution.OActivityLog dtcOActivityLog
	*/
	public Optional<io.vertigo.orchestra.domain.execution.OActivityLog> getActivityLogByAceId(final Long aceId) {
		final Task task = createTaskBuilder("TK_GET_ACTIVITY_LOG_BY_ACE_ID")
				.addValue("ACE_ID", aceId)
				.build();
		return Optional.ofNullable((io.vertigo.orchestra.domain.execution.OActivityLog)getTaskManager()
				.execute(task)
				.getResult());
	}


}
