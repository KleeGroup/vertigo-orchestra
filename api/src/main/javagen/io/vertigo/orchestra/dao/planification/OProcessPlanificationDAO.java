package io.vertigo.orchestra.dao.planification;

import javax.inject.Inject;
import io.vertigo.core.Home;
import io.vertigo.lang.Option;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.planification.OProcessPlanification;

/**
 * DAO : Accès à un object (DTO, DTC).
 * OProcessPlanificationDAO
 */
public final class OProcessPlanificationDAO extends DAOBroker<OProcessPlanification, java.lang.Long> {

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
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_PROCESS_TO_EXECUTE.
	 * @param nodeName String
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> dtcOProcessPlanification
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> getProcessToExecute(final String nodeName) {
		final Task task = createTaskBuilder("TK_GET_PROCESS_TO_EXECUTE")
				.addValue("NODE_NAME", nodeName)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_LAST_PLANIFICATION_BY_PRO_ID.
	 * @param proId Long
	 * @return Option de io.vertigo.orchestra.domain.planification.OProcessPlanification dtOProcessPlanification
	*/
	public Option<io.vertigo.orchestra.domain.planification.OProcessPlanification> getLastPlanificationByProId(final Long proId) {
		final Task task = createTaskBuilder("TK_GET_LAST_PLANIFICATION_BY_PRO_ID")
				.addValue("PRO_ID", proId)
				.build();
		return Option.option((io.vertigo.orchestra.domain.planification.OProcessPlanification) getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TK_GET_PLANIFICATIONS_BY_PRO_ID.
	 * @param proId Long
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> dtcOProcessPlanification
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.planification.OProcessPlanification> getPlanificationsByProId(final Long proId) {
		final Task task = createTaskBuilder("TK_GET_PLANIFICATIONS_BY_PRO_ID")
				.addValue("PRO_ID", proId)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
