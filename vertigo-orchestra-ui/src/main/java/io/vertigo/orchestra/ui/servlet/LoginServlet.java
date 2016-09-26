package io.vertigo.orchestra.ui.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.vertigo.app.Home;
import io.vertigo.persona.security.UserSession;
import io.vertigo.persona.security.VSecurityManager;

/**
 * Servlet pour le login.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = -993990655023270219L;

	private static final String USER_SESSION = "vertigo.webservice.Session";
	private static final String LOGIN_ADMIN = "admin";
	private static final String PASSWORD_ADMIN = "pass";

	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final VSecurityManager securityManager = Home.getApp().getComponentSpace().resolve(VSecurityManager.class);
		try {

			// On recupere les parametres login et password
			final String login = request.getParameter("login");
			final String pwd = request.getParameter("password");

			//We remove the previous user
			request.getSession().removeAttribute(USER_SESSION);

			//We remove from thread the previous user
			securityManager.stopCurrentUserSession();
			final UserSession userSession = securityManager.createUserSession();
			//bind to the thread
			securityManager.startCurrentUserSession(userSession);
			//add to session
			request.getSession().setAttribute(USER_SESSION, userSession);
			// authenticate
			if (LOGIN_ADMIN.equals(login) && PASSWORD_ADMIN.equals(pwd)) {
				userSession.authenticate();
			} else {
				request.setAttribute("errorMessage", "Identification incorrecte");
				request.getRequestDispatcher("./login").forward(request, response);
				return;
			}
			//redirect to SPA
			response.sendRedirect("./");

		} finally {
			// On retire le user du ThreadLocal (il est déjà en session)
			securityManager.stopCurrentUserSession();
		}
	}

}
