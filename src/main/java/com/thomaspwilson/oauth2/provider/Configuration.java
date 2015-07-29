package com.thomaspwilson.oauth2.provider;

import com.thomaspwilson.oauth2.provider.service.ClientService;
import com.thomaspwilson.oauth2.provider.service.CodeService;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import com.thomaspwilson.oauth2.provider.service.UserService;
import java.util.Collection;

public interface Configuration {
    /**
     * Retrieves the name used for the OAuth2 basic authentication realm.
     * @return The realm name
     */
    String getRealm();
    /**
     * Retrieves the default length of time an access token is considered valid,
     * in seconds, post-issuance.
     * @return The token lifespan, in seconds
     */
    long getDefaultAccessTokenLifespan();
    /**
     * Retrieves the default length of time a refresh token is considered valid,
     * in seconds, post-issuance.
     * @return The token lifespan, in seconds
     */
    long getDefaultRefreshTokenLifespan();
    /**
     * Return whether or not the provider should permit all grant types for a
     * client that does not specify allowed grant types.
     * @return True if permissive, false if restrictive
     */
    boolean permissiveGrantTypes();
    /**
     * Return whether or not the provider should support issuing refresh tokens.
     * If true, a clients must still allow the <code>refresh_token</code> grant
     * type.
     * @return True if refresh tokens are supported, false otherwise
     */
    boolean allowRefreshTokens();
    /**
     * Determines whether or not the provider is permitted to reuse refresh
     * tokens. If true, the provider may allow the client to reuse a given
     * refresh token repeatedly.
     * @return True if reuse is allowed, false otherwise
     */
    boolean allowRefreshTokenReuse();
    /**
     * Determines whether or not the provider should accept client secrets
     * provided in the body of the request. This is not recommended according to
     * the specification, which states that applications should instead prefer
     * to send the password using the HTTP Basic authentication scheme.
     * {@link https://tools.ietf.org/html/rfc6749#section-2.3.1}
     * @return True if client secrets are permitted in-body
     */
    boolean allowClientSecretInBody();
    /**
     * Determines whether or not the provider should narrow the requested scope
     * to the scope allowed or return an <code>invalid_scope</code> error when
     * the scope requested is too wide.
     * {@link https://tools.ietf.org/html/rfc6749#section-3.3}
     * @return True if scope narrowing is allowed
     */
    boolean allowScopeNarrowing();
    
    //~ Services ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    TokenService getTokenService();
    CodeService getCodeService();
    UserService getUserService();
    ClientService getClientService();
    
    //~ Token Granters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    Collection<Class<? extends TokenGranter>> getTokenGranters();
}