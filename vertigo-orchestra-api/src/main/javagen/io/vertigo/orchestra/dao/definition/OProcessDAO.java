package io.vertigo.orchestra.dao.definition;

import javax.inject.Inject;
import io.vertigo.app.Home;
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
	 * Execute la tache TK_GET_RECURRENT_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcProcesses
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getRecurrentProcesses() {
		final Task task = createTaskBuilder("TK_GET_RECURRENT_PROCESSES")
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
