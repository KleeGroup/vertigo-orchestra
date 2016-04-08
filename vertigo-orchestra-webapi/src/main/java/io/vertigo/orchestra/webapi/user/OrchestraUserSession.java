package io.vertigo.orchestra.webapi.user;

import java.util.Locale;

import io.vertigo.persona.security.UserSession;

/**
 * UserSession.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class OrchestraUserSession extends UserSession {

	private static final long serialVersionUID = 1782541593145943505L;

	/** {@inheritDoc} */
	@Override
	public Locale getLocale() {
		return Locale.FRANCE;
	}

}
