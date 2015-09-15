package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import io.vertigo.dynamo.impl.store.util.DAOBroker;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OProcessExecution;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OProcessExecutionDAO
 */
public final class OProcessExecutionDAO extends DAOBroker<OProcessExecution, java.lang.Long> {
	 
	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OProcessExecutionDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OProcessExecution.class, storeManager, taskManager);
	}
	

}
