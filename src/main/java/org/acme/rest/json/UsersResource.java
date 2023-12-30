package org.acme.rest.json;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.jboss.resteasy.reactive.NoCache;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;


@Path("/api/users")
// @Authenticated
public class UsersResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Path("/me")
    // @RolesAllowed("user")
    @Authenticated
    @NoCache
    public User me() {
        return new User(securityIdentity);
    }

    public static class User {

        private final String userName;

        User(SecurityIdentity securityIdentity) {
            this.userName = securityIdentity.getPrincipal().getName();
        }

        public String getUserName() {
            return userName;
        }
    }

}
