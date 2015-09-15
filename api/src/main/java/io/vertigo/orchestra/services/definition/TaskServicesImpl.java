package io.vertigo.orchestra.services.definition;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.orchestra.dao.definition.OTaskDAO;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class TaskServicesImpl implements TaskServices {

	@Inject
	private OTaskDAO taskDAO;

	/** {@inheritDoc} */
	@Override
	public void saveTask(final OTask task) {
		taskDAO.save(task);

	}

}
