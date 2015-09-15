package io.vertigo.orchestra.webservices.authentication;

import io.vertigo.vega.rest.RestfulService;
import io.vertigo.vega.rest.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.rest.stereotype.GET;
import io.vertigo.vega.rest.stereotype.PathParam;
import io.vertigo.vega.rest.stereotype.SessionInvalidate;

/**
 * Webservice about Purchases.
 *
 * @author mlaroche
 */
public final class WsAuthentication implements RestfulService {

    /**
     * Get purchase by identifier.
     *
     * @param purId identifier
     * @return people
     */
    @GET("/connect/{login}")
    @AnonymousAccessAllowed
    public void connectUser(@PathParam("login") final String login) {
    }

    /**
     * Get purchase by identifier.
     */
    @GET("/disconnect")
    @SessionInvalidate
    public void disconnect() {
    }
}
