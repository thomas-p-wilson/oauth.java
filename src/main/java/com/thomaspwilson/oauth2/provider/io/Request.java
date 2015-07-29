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

import com.thomaspwilson.oauth2.provider.GrantType;
import com.thomaspwilson.oauth2.provider.util.StringUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Request implements Serializable {
    protected String client_id;
    protected String client_secret;
    protected String grant_type;
    protected Set<String> scope = new HashSet<>();
    protected Set<String> resourceIds = new HashSet<>();
    protected String code;
    /**
     * The resolved redirect URI of this request. A URI may be present in the
     * original request, in the authorizationParameters, or it may not be
     * provided, in which case it will be defaulted (by processing classes) to
     * the Client's default registered value.
     */
    protected String redirect_uri;
    protected String response_type;
    protected String refresh_token;
    
    
    protected Map<String, List<String>> headers = new HashMap<>();
    protected Map<String, List<String>> parameters = new HashMap<>();

    //~ Construction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    protected Request() {}
    public Request(final Request request) {
        this.client_id = request.client_id;
        this.client_secret = request.client_secret;
        this.grant_type = request.grant_type;
        this.scope = new HashSet<>(request.scope);
        this.resourceIds = new HashSet<>(request.resourceIds);
        this.code = request.code;
        this.redirect_uri = request.redirect_uri;
        this.response_type = request.response_type;
        this.refresh_token = request.refresh_token;
        this.headers = new HashMap<>(request.headers);
        this.parameters = new HashMap<>(request.parameters);
    }
    
    //~ Getters and ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getClientId() {
        return client_id;
    }
    public String getClientSecret() {
        return client_secret;
    }
    /**
     * Tries to discover the grant type requested for the token associated with
     * this request.
     *
     * @return the grant type if known, or null otherwise
     */
    public String getGrantType() {
        if (!StringUtils.isBlank(grant_type)) {
            return grant_type;
        }
        if (!StringUtils.isBlank(response_type)
                && response_type.contains("token")) {
            return GrantType.IMPLICIT;
        }
        return null;
    }
    public Set<String> getScope() {
        return new HashSet<>(scope); // Defensive copy
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }
    
    public String getCode() {
        return code;
    }

    public String getRedirectUri() {
        return redirect_uri;
    }

    public String getResponseType() {
        return response_type;
    }
    
    public String getRefreshToken() {
        return refresh_token;
    }
    
    public Map<String, List<String>> getHeaders() {
        return headers;
    }
    
    public Map<String, List<String>> getParameters() {
        return parameters;
    }


    //~ Object impl ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((client_id == null) ? 0 : client_id.hashCode());
        result = prime * result + ((client_secret == null) ? 0 : client_secret.hashCode());
        result = prime * result + ((grant_type == null) ? 0 : grant_type.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + ((redirect_uri == null) ? 0 : redirect_uri.hashCode());
        result = prime * result + ((response_type == null) ? 0 : response_type.hashCode());
        result = prime * result + ((refresh_token == null) ? 0 : refresh_token.hashCode());
        result = prime * result + ((headers == null) ? 0 : headers.hashCode());
        result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Request other = (Request) obj;
        if ((client_id == null ^ other.client_id == null)
                || !client_id.equals(other.client_id)) {
            return false;
        }
        if ((client_secret == null ^ other.client_secret == null)
                || !client_secret.equals(other.client_secret)) {
            return false;
        }
        if ((grant_type == null ^ other.grant_type == null)
                || !grant_type.equals(other.grant_type)) {
            return false;
        }
        if ((scope == null ^ other.scope == null)
                || !scope.equals(other.scope)) {
            return false;
        }
        if ((resourceIds == null ^ other.resourceIds == null)
                || !resourceIds.equals(other.resourceIds)) {
            return false;
        }
        if ((code == null ^ other.code == null)
                || !code.equals(other.code)) {
            return false;
        }
        if ((redirect_uri == null ^ other.redirect_uri == null)
                || !redirect_uri.equals(other.redirect_uri)) {
            return false;
        }
        if ((response_type == null ^ other.response_type == null)
                || !response_type.equals(other.response_type)) {
            return false;
        }
        if ((refresh_token == null ^ other.refresh_token == null)
                || !refresh_token.equals(other.refresh_token)) {
            return false;
        }
        if ((headers == null ^ other.headers == null)
                || !headers.equals(other.headers)) {
            return false;
        }
        if ((parameters == null ^ other.parameters == null)
                || !parameters.equals(other.parameters)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName()+"@"+hashCode()+"[\n"
                + "  client_id="+client_id+"\n"
                + "  client_secret="+(client_secret == null ? null : "********")+"\n"
                + "  grant_type="+grant_type+"\n"
                + "  scope="+scope+"\n"
                + "  resource_ids="+resourceIds+"\n"
                + "  redirect_uri="+redirect_uri+"\n"
                + "  response_type="+response_type+"\n"
                + "  refresh_token="+refresh_token+"\n"
                + "  headers="+headers+"\n"
                + "  bodyParameters="+parameters+"\n"
                + "]";
    }
}