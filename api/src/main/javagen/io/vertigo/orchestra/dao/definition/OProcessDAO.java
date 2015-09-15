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
import io.vertigo.orchestra.domain.definition.OProcess;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OProcessDAO
 */
public final class OProcessDAO extends DAOBroker<OProcess, java.lang.Long> {
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_GET_ACTIVES_PROCESSES */
		TK_GET_ACTIVES_PROCESSES,
	}

	/** Constante de paramètre de la tache DTC_PROCESSES. */
	private static final String ATTR_OUT_TK_GET_ACTIVES_PROCESSES_DTC_PROCESSES = "DTC_PROCESSES";

	 
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
	 * Création d'une tache.
	 * @param task Type de la tache
	 * @return Builder de la tache
	 */
	private TaskBuilder createTaskBuilder(final Tasks task) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(task.toString(), TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_ACTIVES_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcProcesses
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getActivesProcesses() {
		final Task task = createTaskBuilder(Tasks.TK_GET_ACTIVES_PROCESSES)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_ACTIVES_PROCESSES_DTC_PROCESSES);
	}


}
