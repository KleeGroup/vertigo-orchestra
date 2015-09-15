package io.vertigo.orchestra.dao.referential;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.referential.PlanificationState;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * PlanificationStateDAO
 */
public final class PlanificationStateDAO extends DAOBroker<PlanificationState, java.lang.String> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public PlanificationStateDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(PlanificationState.class, storeManager, taskManager);
	}
	

}
