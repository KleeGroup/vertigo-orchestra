package io.vertigo.orchestra.impl.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

import javax.inject.Inject;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessDefinitionManagerImpl implements ProcessDefinitionManager {

	@Inject
	private OProcessDAO processDao;
	@Inject
	private OTaskDAO taskDAO;

	/** {@inheritDoc} */
	@Override
	public DtList<OProcess> getActiveProcesses() {
		return processDao.getActivesProcesses();
	}

	/** {@inheritDoc} */
	@Override
	public void saveProcess(final OProcess process) {
		processDao.save(process);

	}

	/** {@inheritDoc} */
	@Override
	public OTask getFirtTaskByProcess(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return taskDAO.getFirstTaskByProcess(proId);
	}

	/** {@inheritDoc} */
	@Override
	public void saveTask(final OTask task) {
		taskDAO.save(task);

	}
}
