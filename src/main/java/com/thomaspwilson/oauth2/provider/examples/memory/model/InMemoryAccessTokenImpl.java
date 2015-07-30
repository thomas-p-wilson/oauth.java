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

package com.thomaspwilson.oauth2.provider.examples.memory.model;

import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.User;
import java.util.Set;
import org.joda.time.DateTime;

public class InMemoryAccessTokenImpl implements AccessToken {
    private final String id;
    private String type;
    private Set<String> scope;
    private InMemoryRefreshTokenImpl refreshToken;
    private InMemoryAuthorizationCodeImpl authorizationCode;
    private Client client;
    private User user;
    private Request originalRequest;
    private DateTime issueTime;
    
    public InMemoryAccessTokenImpl(final String id) {
        this.id = id;
    }

    @Override
    public String getTokenId() {
        return id;
    }
    
    @Override
    public String getTokenType() {
        return type;
    }
    public InMemoryAccessTokenImpl setTokenType(final String type) {
        this.type = type;
        return this;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }
    public InMemoryAccessTokenImpl setScope(final Set<String> scope) {
        this.scope = scope;
        return this;
    }

    @Override
    public String getRefreshTokenId() {
        if (refreshToken != null) {
            return refreshToken.getTokenId();
        }
        return null;
    }
    public InMemoryAccessTokenImpl setRefreshToken(final InMemoryRefreshTokenImpl refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Override
    public String getAuthorizationCodeId() {
        if (authorizationCode != null) {
            return authorizationCode.getId();
        }
        return null;
    }
    public InMemoryAccessTokenImpl setAuthorizationCode(final InMemoryAuthorizationCodeImpl authorizationCode) {
        this.authorizationCode = authorizationCode;
        return this;
    }

    public Client getClient() {
        return client;
    }
    @Override
    public String getClientId() {
        if (client != null) {
            return client.getId();
        }
        return null;
    }
    public InMemoryAccessTokenImpl setClient(final Client client) {
        this.client = client;
        return this;
    }

    public User getUser() {
        return user;
    }
    @Override
    public String getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null;
    }
    public InMemoryAccessTokenImpl setUser(final User user) {
        this.user = user;
        return this;
    }

    @Override
    public Request getOriginalRequest() {
        return originalRequest;
    }
    public InMemoryAccessTokenImpl setOriginalRequest(final Request originalRequest) {
        this.originalRequest = originalRequest;
        return this;
    }

    @Override
    public DateTime getIssueTime() {
        return issueTime;
    }
    public InMemoryAccessTokenImpl setIssueTime(final DateTime issueTime) {
        this.issueTime = issueTime;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getName()+"@"+hashCode()+"[\n"
                + "  id=" + id + "\n"
                + "  type=" + type + "\n"
                + "  scope=" + scope + "\n"
                + "  refreshToken=" + refreshToken + "\n"
                + "  client=" + client + "\n"
                + "  user=" + user + "\n"
                + "  originalRequest=" + originalRequest + "\n"
                + "  issueTime=" + issueTime + "\n"
                + "]";
    }
    
    
}