package io.vertigo.orchestra.webapi.services.impl;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.dao.definition.OProcessDAO;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.webapi.services.DefinitionServices;

/**
 * Implementation of access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class DefinitionServicesImpl implements DefinitionServices {

	@Inject
	private OProcessDAO processDAO;
	@Inject
	private OActivityDAO activityDAO;

	/** {@inheritDoc} */
	@Override
	public OProcess getProcessDefinitionById(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return processDAO.get(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OActivity> getActivitiesByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return activityDAO.getActivitiesByProId(proId);
	}

}
