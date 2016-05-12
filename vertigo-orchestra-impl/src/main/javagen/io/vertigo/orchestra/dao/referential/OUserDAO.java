package io.vertigo.orchestra.dao.referential;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.referential.OUser;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OUserDAO
 */
public final class OUserDAO extends DAOBroker<OUser, java.lang.Long> implements StoreServices {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OUserDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OUser.class, storeManager, taskManager);
	}
	

}
