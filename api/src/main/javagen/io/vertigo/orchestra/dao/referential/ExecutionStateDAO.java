package io.vertigo.orchestra.dao.referential;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.referential.ExecutionState;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * ExecutionStateDAO
 */
public final class ExecutionStateDAO extends DAOBroker<ExecutionState, java.lang.String> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public ExecutionStateDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(ExecutionState.class, storeManager, taskManager);
	}
	

}
