/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thomaspwilson.oauth2.provider;

import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.exception.UnsupportedGrantTypeException;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import com.thomaspwilson.oauth2.provider.service.ClientService;
import com.thomaspwilson.oauth2.provider.service.CodeService;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import com.thomaspwilson.oauth2.provider.service.UserService;
import com.thomaspwilson.oauth2.provider.util.StringUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessTokenManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessTokenManager.class);
    protected final Configuration config;
    protected final TokenService tokenService;
    protected final CodeService codeService;
    protected final UserService userService;
    protected final ClientService clientService;
    protected final Set<TokenGranter> tokenGranters = new HashSet<>();

    //~ Construction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public AccessTokenManager(final Configuration config) throws InstantiationException {
        this.config = config;
        this.tokenService = config.getTokenService();
        this.codeService = config.getCodeService();
        this.userService = config.getUserService();
        this.clientService = config.getClientService();
        
        
        try {
            for (Class<? extends TokenGranter> granter : config.getTokenGranters()) {
                tokenGranters.add(
                        granter.getConstructor(AccessTokenManager.class).newInstance(this));
            }
        } catch (IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException ex) {
            LOGGER.error("", ex);
            throw new InstantiationException(ex.getMessage());
        }
    }

    public AccessToken request(final Request request) {
        if (StringUtils.isBlank(request.getGrantType())) {
            throw new InvalidRequestException("Missing grant type");
        }
        if ("implicit".equals(request.getGrantType())) {
            throw new InvalidGrantException("Implicit grant type not supported from token endpoint");
        }

        boolean found = false;
        for (TokenGranter granter : tokenGranters) {
            if (!granter.grantType().equals(request.getGrantType())) {
                continue;
            }
            found = true;
            AccessToken token = granter.grant(request);
            if (token != null) {
                return token;
            }
        }
        if (!found) {
            throw new UnsupportedGrantTypeException();
        }
        return null;
    }

    public AccessToken createAccessToken(Request request, OAuth2Principal principal) {
        
        AccessToken existingAccessToken = tokenService.findAccessToken(principal);
        if (existingAccessToken != null) {
            return existingAccessToken;
        }
        
        AccessToken accessToken = tokenService.createAccessToken(request, principal);
        tokenService.save(accessToken);
        
        RefreshToken refreshToken = tokenService.findRefreshToken(accessToken.getRefreshTokenId());
        // If we're not to build a refresh token, set it to null just in case
        if (!config.allowRefreshTokens()
                || !principal.getClient().getAllowedGrantTypes().contains(Parameters.REFRESH_TOKEN)) {
            refreshToken = null;
        }
        // Only create a new refresh token if there wasn't an existing one
        // associated with an expired access token.
        // Clients might be holding existing refresh tokens, so we re-use it in
        // the case that the old access token
        // expired.
        else if (refreshToken == null) {
            refreshToken = tokenService.createRefreshToken(accessToken);
        }
        if (refreshToken != null) {
            tokenService.save(refreshToken);
        }
        return accessToken;

    }
    
    public AccessToken createAccessToken(Request request, AuthorizationCode code) {
        Client client = clientService.find(code.getClientId());
        OAuth2Principal principal = new OAuth2Principal(client, null);
        return tokenService.createAccessToken(request, principal, code);
    }
    
    public Configuration getConfig() {
        return config;
    }
    public TokenService getTokenService() {
        return tokenService;
    }
    public CodeService getCodeService() {
        return codeService;
    }
    public UserService getUserService() {
        return userService;
    }
    public ClientService getClientService() {
        return clientService;
    }
}
