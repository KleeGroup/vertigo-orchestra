package io.vertigo.orchestra.dao.definition;

import javax.inject.Inject;
import io.vertigo.core.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.task.model.TaskResult;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OTaskDAO
 */
public final class OTaskDAO extends DAOBroker<OTask, java.lang.Long> {
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_GET_FIRST_TASK_BY_PROCESS */
		TK_GET_FIRST_TASK_BY_PROCESS,
	}

	/** Constante de paramètre de la tache PRO_ID. */
	private static final String ATTR_IN_TK_GET_FIRST_TASK_BY_PROCESS_PRO_ID = "PRO_ID";

	/** Constante de paramètre de la tache DT_O_TASK. */
	private static final String ATTR_OUT_TK_GET_FIRST_TASK_BY_PROCESS_DT_O_TASK = "DT_O_TASK";

	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OTaskDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OTask.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_FIRST_TASK_BY_PROCESS.
	 * @param proId Long 
	 * @return io.vertigo.orchestra.domain.definition.OTask dtOTask
	*/
	public io.vertigo.orchestra.domain.definition.OTask getFirstTaskByProcess(final Long proId) {
		final Task task = createTaskBuilder(Tasks.TK_GET_FIRST_TASK_BY_PROCESS)
				.addValue(ATTR_IN_TK_GET_FIRST_TASK_BY_PROCESS_PRO_ID, proId)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_FIRST_TASK_BY_PROCESS_DT_O_TASK);
	}


}
