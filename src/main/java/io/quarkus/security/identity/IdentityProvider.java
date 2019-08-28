package io.quarkus.security.identity;

import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.function.Supplier;

import io.quarkus.security.AuthenticationFailedException;
import io.quarkus.security.identity.request.AuthenticationRequest;

/**
 * <p>
 * As the requirements for different providers are often different this interface has purposefully been
 * made minimal. Interfaces that extend IdentityProvider should be created for different use cases, which can
 * then be used directly.
 * <p>
 * {@link IdentityProviderManager} should be used to get a specific type of {@link IdentityProvider} for a specific
 * use case.
 */
public interface IdentityProvider<T extends AuthenticationRequest> {

    /**
     *
     * @return The type of request this store can handle
     */
    Class<T> getRequestType();

    /**
     * Attempts to authenticate the given authentication request. If this is unsuccessful because the provided
     * credentials are invalid then this should fail with an {@link AuthenticationFailedException}, otherwise
     * it should complete with a null value.
     * <p>
     * Any other failure mode will be interpreted as a failure in the underlying store (e.g. LDAP is down), rather
     * than the credentials being invalid. If multiple providers are installed that can handle the same type of request
     * an {@link AuthenticationFailedException} will immediately stop processing, while any other failure will allow
     * processing to continue.
     *
     * @param request The authentication request
     * @param context The context of the request
     * @return The future security identity
     */
    CompletionStage<SecurityIdentity> authenticate(T request, AuthenticationRequestContext context);


}
