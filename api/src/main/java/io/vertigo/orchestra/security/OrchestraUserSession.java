package io.vertigo.orchestra.security;

import java.util.Locale;

import io.vertigo.persona.security.UserSession;

/**
 * Session d'un utilisateur<br>
 * Un utilisateur poss�de une liste de profils correspondant � des r�les au sein d'une ou plusieurs entit�s.<br>
 * On consid�re que toute session utilisateur cr��e implique que l'utilisateur est authentifi�.
 *
 * @author mlaroche
 * @version $Id: IcdpiUserSession.java 562 2012-03-08 12:18:42Z adufranne $
 */
public class OrchestraUserSession extends UserSession {

    /**
     * Serial Version.
     */
    private static final long serialVersionUID = 2497388902473962429L;

    /**
     * Constructeur.
     */
    public OrchestraUserSession() {
    }

    /** {@inheritDoc} */
    @Override
    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }
}
