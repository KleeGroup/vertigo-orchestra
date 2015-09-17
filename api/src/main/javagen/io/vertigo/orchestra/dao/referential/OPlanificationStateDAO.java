package io.vertigo.orchestra.dao.referential;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.referential.OPlanificationState;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OPlanificationStateDAO
 */
public final class OPlanificationStateDAO extends DAOBroker<OPlanificationState, java.lang.String> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OPlanificationStateDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OPlanificationState.class, storeManager, taskManager);
	}
	

}
