package io.vertigo.orchestra.dao.planification;

import javax.inject.Inject;
import io.vertigo.core.Home;
import io.vertigo.lang.Option;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.task.model.TaskResult;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OProcessPlanificationDAO
 */
public final class OProcessPlanificationDAO extends DAOBroker<OProcessPlanification, java.lang.Long> {
	/** Liste des taches. */
	private enum Tasks {
		/** Tache TK_GET_PROCESS_TO_EXECUTE */
		TK_GET_PROCESS_TO_EXECUTE,
		/** Tache TK_GET_LAST_PLANIFICATION_BY_PRO_ID */
		TK_GET_LAST_PLANIFICATION_BY_PRO_ID,
	}

	/** Constante de paramètre de la tache DTC_O_PROCESS_PLANIFICATION. */
	private static final String ATTR_OUT_TK_GET_PROCESS_TO_EXECUTE_DTC_O_PROCESS_PLANIFICATION = "DTC_O_PROCESS_PLANIFICATION";

	/** Constante de paramètre de la tache PRO_ID. */
	private static final String ATTR_IN_TK_GET_LAST_PLANIFICATION_BY_PRO_ID_PRO_ID = "PRO_ID";

	/** Constante de paramètre de la tache DT_O_PROCESS_PLANIFICATION. */
	private static final String ATTR_OUT_TK_GET_LAST_PLANIFICATION_BY_PRO_ID_DT_O_PROCESS_PLANIFICATION = "DT_O_PROCESS_PLANIFICATION";

	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OProcessPlanificationDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OProcessPlanification.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_PROCESS_TO_EXECUTE.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> dtcOProcessPlanification
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> getProcessToExecute() {
		final Task task = createTaskBuilder(Tasks.TK_GET_PROCESS_TO_EXECUTE)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return taskResult.getValue(ATTR_OUT_TK_GET_PROCESS_TO_EXECUTE_DTC_O_PROCESS_PLANIFICATION);
	}

	/**
	 * Execute la tache TK_GET_LAST_PLANIFICATION_BY_PRO_ID.
	 * @param proId Long 
	 * @return Option de io.vertigo.orchestra.domain.planification.OProcessPlanification dtOProcessPlanification
	*/
	public Option<io.vertigo.orchestra.domain.planification.OProcessPlanification> getLastPlanificationByProId(final Long proId) {
		final Task task = createTaskBuilder(Tasks.TK_GET_LAST_PLANIFICATION_BY_PRO_ID)
				.addValue(ATTR_IN_TK_GET_LAST_PLANIFICATION_BY_PRO_ID_PRO_ID, proId)
				.build();
		final TaskResult taskResult = getTaskManager().execute(task);
		return Option.option(taskResult.<io.vertigo.orchestra.domain.planification.OProcessPlanification> getValue(ATTR_OUT_TK_GET_LAST_PLANIFICATION_BY_PRO_ID_DT_O_PROCESS_PLANIFICATION));
	}


}
