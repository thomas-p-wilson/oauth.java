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
package com.thomaspwilson.oauth2.provider.grant;

import com.thomaspwilson.oauth2.provider.AccessTokenManager;
import com.thomaspwilson.oauth2.provider.OAuth2Principal;
import com.thomaspwilson.oauth2.provider.TokenGranter;
import com.thomaspwilson.oauth2.provider.exception.InvalidClientException;
import com.thomaspwilson.oauth2.provider.exception.InvalidScopeException;
import com.thomaspwilson.oauth2.provider.exception.UnauthorizedClientException;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.User;
import com.thomaspwilson.oauth2.provider.service.ClientService;
import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTokenGranter implements TokenGranter {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTokenGranter.class);
    protected final AccessTokenManager tokenManager;
    protected final ClientService clientService;

    protected AbstractTokenGranter(final AccessTokenManager tokenManager) {
        this.tokenManager = tokenManager;
        this.clientService = tokenManager.getClientService();
    }

    @Override
    public AccessToken grant(final Request request) {
        if (!grantType().equals(request.getGrantType())) {
            return null;
        }
        
        validateRequest(request);
        
        Client client = authenticateClient(request);
        User user = authenticateUser(request);
        OAuth2Principal principal = new OAuth2Principal(client, user);
        validateGrant(request, principal);
        validateScope(request, principal);
        
        return tokenManager.createAccessToken(request, principal);
    }
    
    protected void validateRequest(final Request request) {}
    
    protected Client authenticateClient(final Request request) {
        Client client = clientService.find(request.getClientId());
        if (!client.getSecret().equals(request.getClientSecret())) {
            throw new InvalidClientException("Invalid credentials");
        }
        return client;
    }
    protected User authenticateUser(final Request request) {
        return null;
    }

    protected void validateGrant(final Request request,
            final OAuth2Principal principal) {
        final Collection<String> authorizedGrantTypes = principal.getClient().getAllowedGrantTypes();
        if (authorizedGrantTypes == null || authorizedGrantTypes.isEmpty()) {
            if (tokenManager.getConfig().permissiveGrantTypes()) {
                return;
            }
            throw new UnauthorizedClientException("Client not authorized for grant "+request.getGrantType());
        }
        if (!authorizedGrantTypes.contains(request.getGrantType())) {
            throw new UnauthorizedClientException("Client not authorized for grant "+request.getGrantType());
        }
    }
    protected void validateScope(final Request request,
            final OAuth2Principal principal) {
        Set<String> disallowed = request.getScope();
        disallowed.removeAll(principal.getClient().getAllowedScopes());
        if (!disallowed.isEmpty()) {
            throw new InvalidScopeException("Client is not authorized for scope "+disallowed);
        }
    }

}