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
package com.thomaspwilson.oauth2.provider.grant.clientcredentials;

import com.thomaspwilson.oauth2.provider.AccessTokenManager;
import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.Parameters;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.exception.UnauthorizedClientException;
import com.thomaspwilson.oauth2.provider.grant.AbstractTokenGranter;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.util.StringUtils;

public class ClientCredentialsTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "client_credentials";

    public ClientCredentialsTokenGranter(final AccessTokenManager tokenManager) {
        super(tokenManager);
    }

    @Override
    protected void validateRequest(Request request) {
        if (StringUtils.isBlank(request.getClientId())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_ID);
        }
        if (StringUtils.isBlank(request.getClientSecret())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_SECRET);
        }
    }

    @Override
    protected Client authenticateClient(Request request) {
        Client client = super.authenticateClient(request);
        if (!client.getType().equals(ClientType.CONFIDENTIAL)) {
            throw new UnauthorizedClientException("Public client not authorized to use "+GRANT_TYPE+" grant type");
        }
        return client;
    }

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }
}