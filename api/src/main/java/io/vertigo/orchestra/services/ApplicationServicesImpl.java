package io.vertigo.orchestra.services;

import javax.inject.Inject;

import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.orchestra.dao.application.ApplicationPAO;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class ApplicationServicesImpl implements ApplicationServices {

	@Inject
	private ApplicationPAO applicationPAO;

	/** {@inheritDoc} */
	@Override
	public void deleteAll() {
		applicationPAO.deleteAll();

	}

}
