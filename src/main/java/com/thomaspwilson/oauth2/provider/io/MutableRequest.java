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
package com.thomaspwilson.oauth2.provider.io;

import com.thomaspwilson.oauth2.provider.Parameters;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.util.MapUtils;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;

public class MutableRequest extends Request {
    
    //~ Construction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public MutableRequest() {}
    public MutableRequest(final Request request) {
        super(request);
    }
    public MutableRequest(final Map<String, List<String>> headers,
            final Map<String, List<String>> parameters) {
        if (headers.containsKey("Authorization")) {
            // Grab client id and secret from basic auth
            String header = headers.get("Authorization").get(0);
            if (header.toLowerCase().startsWith("basic")) {
                header = header.substring(5).trim();
                String decoded = new String(DatatypeConverter.parseBase64Binary(header), StandardCharsets.UTF_8);
                String[] parts = decoded.split(":", 2);
                if (parts.length == 2) {
                    client_id = parts[0];
                    client_secret = parts[1];
                }
            }
        }
        if (parameters.containsKey(Parameters.CLIENT_ID)) {
            if (client_id != null) {
                throw new InvalidRequestException("Duplicated parameter "+Parameters.CLIENT_ID);
            }
            client_id = MapUtils.getOnlyOrNull(parameters, Parameters.CLIENT_ID);
        }
        if (parameters.containsKey(Parameters.CLIENT_SECRET)) {
            if (client_secret != null) {
                throw new InvalidRequestException("Duplicated parameter "+Parameters.CLIENT_SECRET);
            }
            client_secret = MapUtils.getOnlyOrNull(parameters, Parameters.CLIENT_SECRET);
        }
        grant_type = MapUtils.getOnlyOrNull(parameters, Parameters.GRANT_TYPE);
        scope = new HashSet<>(Arrays.asList(MapUtils.getOnlyOrEmpty(parameters, Parameters.SCOPE).split(" ")));
        code = MapUtils.getOnlyOrNull(parameters, Parameters.CODE);
        redirect_uri = MapUtils.getOnlyOrNull(parameters, Parameters.REDIRECT_URI);
        response_type = MapUtils.getOnlyOrNull(parameters, Parameters.RESPONSE_TYPE);
        refresh_token = MapUtils.getOnlyOrNull(parameters, Parameters.REFRESH_TOKEN);
        this.headers = headers;
        this.parameters = parameters;
    }
    
    //~ Setters ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public MutableRequest setClientId(final String client_id) {
        this.client_id = client_id;
        return this;
    }
    public MutableRequest setClientSecret(final String client_secret) {
        this.client_secret = client_secret;
        return this;
    }
    public MutableRequest setGrantType(final String grant_type) {
        this.grant_type = grant_type;
        return this;
    }
    public MutableRequest setScope(final Collection<String> scope) {
        this.scope = new HashSet<>(scope);
        return this;
    }
    public MutableRequest setCode(final String code) {
        this.code = code;
        return this;
    }
    public MutableRequest setRedirectUri(final String redirect_uri) {
        this.redirect_uri = redirect_uri;
        return this;
    }
    public MutableRequest setResponseType(final String response_type) {
        this.response_type = response_type;
        return this;
    }
    public MutableRequest setRefreshToken(final String refresh_token) {
        this.refresh_token = refresh_token;
        return this;
    }
    public MutableRequest setHeaders(
            final Map<String, List<String>> headers) {
        this.headers = headers;
        return this;
    }
    public MutableRequest setParameters(
            final Map<String, List<String>> parameters) {
        this.parameters = parameters;
        return this;
    }
    
    /**
     * Update the scope and create a new request. All the other properties are
     * the same (including the request parameters).
     *
     * @param scope the new scope
     * @return a new request with the narrowed scope
     */
    public MutableRequest narrowScope(final Collection<String> scope) {
        return new MutableRequest(this).setScope(scope);
    }
}
