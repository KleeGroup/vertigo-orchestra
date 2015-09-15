package io.vertigo.orchestra.services.definition;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ProcessServicesImpl implements ProcessServices {

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

}
