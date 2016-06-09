package io.vertigo.orchestra.dao.definition;

import javax.inject.Inject;
import io.vertigo.app.Home;
import io.vertigo.lang.Option;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.definition.OProcess;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OProcessDAO
 */
public final class OProcessDAO extends DAOBroker<OProcess, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OProcessDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OProcess.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_ALL_SCHEDULED_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcProcesses
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getAllScheduledProcesses() {
		final Task task = createTaskBuilder("TK_GET_ALL_SCHEDULED_PROCESSES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_ACTIVE_PROCESS_BY_NAME.
	 * @param name String 
	 * @return Option de io.vertigo.orchestra.domain.definition.OProcess dtProcess
	*/
	public Option<io.vertigo.orchestra.domain.definition.OProcess> getActiveProcessByName(final String name) {
		final Task task = createTaskBuilder("TK_GET_ACTIVE_PROCESS_BY_NAME")
				.addValue("NAME", name)
				.build();
		return Option.of((io.vertigo.orchestra.domain.definition.OProcess)getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TK_GET_ALL_ACTIVE_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcProcesses
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getAllActiveProcesses() {
		final Task task = createTaskBuilder("TK_GET_ALL_ACTIVE_PROCESSES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcOProcess
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getProcesses() {
		final Task task = createTaskBuilder("TK_GET_PROCESSES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}


}
