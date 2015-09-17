package io.vertigo.orchestra.dao.referential;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.referential.OExecutionState;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OExecutionStateDAO
 */
public final class OExecutionStateDAO extends DAOBroker<OExecutionState, java.lang.String> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OExecutionStateDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OExecutionState.class, storeManager, taskManager);
	}
	

}
