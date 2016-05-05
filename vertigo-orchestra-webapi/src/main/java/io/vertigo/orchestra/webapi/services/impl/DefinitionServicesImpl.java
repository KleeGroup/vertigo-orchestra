package io.vertigo.orchestra.webapi.services.impl;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.webapi.dao.uidefinitions.UidefinitionsPAO;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;
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
	private UidefinitionsPAO uiDefinitionsPAO;
	@Inject
	private OActivityDAO activityDAO;

	/** {@inheritDoc} */
	@Override
	public OProcessUi getProcessDefinitionById(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return uiDefinitionsPAO.getProcessByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessUi> searchProcess(final String search) {
		return uiDefinitionsPAO.searchProcessByLabel("%" + search + "%");
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OActivity> getActivitiesByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return activityDAO.getActivitiesByProId(proId);
	}

}
