/*
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
package com.thomaspwilson.oauth2.provider.impl.memory.model;

import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;
import java.util.HashSet;
import java.util.Set;

public class InMemoryAuthorizationCodeImpl implements AuthorizationCode {
    private String id;
    private String clientId;
    private Set<String> scope;
    private String redirectUri;
    private boolean isRedeemed;
    
    public InMemoryAuthorizationCodeImpl(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getClientId() {
        return clientId;
    }
    public InMemoryAuthorizationCodeImpl setClientId(final String clientId) {
        this.clientId = clientId;
        return this;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }
    public InMemoryAuthorizationCodeImpl setScope(final Set<String> scope) {
        this.scope = new HashSet<>(scope);
        return this;
    }

    @Override
    public String getRedirectUri() {
        return redirectUri;
    }
    public InMemoryAuthorizationCodeImpl setRedirectUri(final String redirectUri) {
        this.redirectUri = redirectUri;
        return this;
    }

    @Override
    public boolean isRedeemed() {
        return isRedeemed;
    }
    @Override
    public AuthorizationCode setIsRedeemed(final boolean isRedeemed) {
        this.isRedeemed = isRedeemed;
        return this;
    }
    
}