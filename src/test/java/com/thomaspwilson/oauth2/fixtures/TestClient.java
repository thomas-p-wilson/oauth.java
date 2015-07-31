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
package com.thomaspwilson.oauth2.fixtures;

import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.model.Client;
import java.util.Set;

public class TestClient implements Client {
    
    private final String id;
    private final String secret;
    private final ClientType type;
    private final Integer accessTokenLifespan;
    private final Integer refreshTokenLifespan;
    private final Set<String> allowedGrantTypes;
    private final Set<String> allowedScopes;
    
    public TestClient(final String id, final String secret, final ClientType type,
            final Integer accessTokenLifespan,
            final Integer refreshTokenLifespan,
            final Set<String> allowedGrantTypes,
            final Set<String> allowedScopes) {
        this.id = id;
        this.secret = secret;
        this.type = type;
        this.accessTokenLifespan = accessTokenLifespan;
        this.refreshTokenLifespan = refreshTokenLifespan;
        this.allowedGrantTypes = allowedGrantTypes;
        this.allowedScopes = allowedScopes;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public ClientType getType() {
        return type;
    }

    @Override
    public Integer getAccessTokenLifespanSeconds() {
        return accessTokenLifespan;
    }

    @Override
    public Integer getRefreshTokenLifespanSeconds() {
        return refreshTokenLifespan;
    }

    @Override
    public Set<String> getAllowedGrantTypes() {
        return allowedGrantTypes;
    }

    @Override
    public Set<String> getAllowedScopes() {
        return allowedScopes;
    }
    
}