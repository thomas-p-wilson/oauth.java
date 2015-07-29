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
package com.thomaspwilson.oauth2.provider.grant.password;

import com.thomaspwilson.oauth2.provider.AccessTokenManager;
import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.Parameters;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.grant.AbstractTokenGranter;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.User;
import com.thomaspwilson.oauth2.provider.service.UserService;
import com.thomaspwilson.oauth2.provider.util.MapUtils;
import com.thomaspwilson.oauth2.provider.util.StringUtils;

public class ResourceOwnerPasswordTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "password";

    private final UserService userService;

    public ResourceOwnerPasswordTokenGranter(AccessTokenManager tokenManager) {
        super(tokenManager);
        this.userService = tokenManager.getUserService();
    }

    @Override
    protected void validateRequest(final Request request) {
        if (StringUtils.isBlank(request.getClientId())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_ID);
        }
        if (StringUtils.isBlank(MapUtils.getOnlyOrNull(request.getParameters(), Parameters.USERNAME))) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.USERNAME);
        }
        if (StringUtils.isBlank(MapUtils.getOnlyOrNull(request.getParameters(), Parameters.PASSWORD))) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.PASSWORD);
        }
    }

    @Override
    protected Client authenticateClient(Request request) {
        // TODO: If client is confidential, was issued client credentials, or
        //       was assigned other auth requirements, client MUST authenticate
        //       as described in https://tools.ietf.org/html/rfc6749#section-3.2.1
        if (!StringUtils.isBlank(request.getClientSecret())) {
            return super.authenticateClient(request);
        }
        Client client = clientService.find(request.getClientId());
        if (client.getType().equals(ClientType.CONFIDENTIAL)) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_SECRET);
        }
        return client;
    }
    
    @Override
    protected User authenticateUser(Request request) {
        String username = MapUtils.getOnlyOrNull(request.getParameters(), Parameters.USERNAME);
        String password = MapUtils.getOnlyOrNull(request.getParameters(), Parameters.PASSWORD);
        // Protect from downstream leaks of password
        request.getParameters().remove(Parameters.PASSWORD);
        
        User user = userService.authenticate(username, password);
        if (user == null) {
            throw new InvalidGrantException("Invalid user credentials");
        }
        return user;
    }

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }
}
