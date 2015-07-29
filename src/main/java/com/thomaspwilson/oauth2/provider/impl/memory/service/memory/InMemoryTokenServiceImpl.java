/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thomaspwilson.oauth2.provider.impl.memory.service.memory;

import com.thomaspwilson.oauth2.provider.OAuth2Principal;
import com.thomaspwilson.oauth2.provider.impl.memory.model.InMemoryAccessTokenImpl;
import com.thomaspwilson.oauth2.provider.impl.memory.model.InMemoryAuthorizationCodeImpl;
import com.thomaspwilson.oauth2.provider.impl.memory.model.InMemoryRefreshTokenImpl;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.joda.time.DateTime;

public class InMemoryTokenServiceImpl implements TokenService {

    private final Map<OAuth2Principal, AccessToken> accessTokens = new HashMap<>();
    private final Map<OAuth2Principal, RefreshToken> refreshTokens = new HashMap<>();

    @Override
    public AccessToken createAccessToken(final Request request,
            final OAuth2Principal principal) {
        InMemoryAccessTokenImpl token = new InMemoryAccessTokenImpl(UUID.randomUUID().toString());
        token.setClient(principal.getClient());
        token.setUser(principal.getUser());
        token.setScope(request.getScope());
        token.setTokenType("bearer");
        token.setIssueTime(new DateTime());
        return token;
    }

    @Override
    public AccessToken createAccessToken(final Request request,
            final OAuth2Principal principal,
            final AuthorizationCode code) {
        InMemoryAccessTokenImpl token = new InMemoryAccessTokenImpl(UUID.randomUUID().toString());
        token.setClient(principal.getClient());
        token.setUser(principal.getUser());
        token.setScope(request.getScope());
        token.setAuthorizationCode((InMemoryAuthorizationCodeImpl)code);
        token.setTokenType("bearer");
        token.setIssueTime(new DateTime());
        return token;
    }

    @Override
    public RefreshToken createRefreshToken(
            final AccessToken sourceAccessToken) {
        return new InMemoryRefreshTokenImpl(
                UUID.randomUUID().toString(),
                (InMemoryAccessTokenImpl)sourceAccessToken,
                new DateTime());
    }

    @Override
    public AccessToken findAccessToken(String value) {
        for (AccessToken at : accessTokens.values()) {
            if (at.getTokenId().equals(value)) {
                return at;
            }
        }
        return null;
    }

    @Override
    public AccessToken findAccessToken(OAuth2Principal principal) {
        return null;
    }

    @Override
    public RefreshToken findRefreshToken(String value) {
        for (Map.Entry<OAuth2Principal, RefreshToken> entry : refreshTokens.entrySet()) {
            if (entry.getValue().getTokenId().equals(value)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public void save(AccessToken accessToken) {
        InMemoryAccessTokenImpl at = (InMemoryAccessTokenImpl)accessToken;
        OAuth2Principal principal = new OAuth2Principal(at.getClient(), at.getUser());
        this.accessTokens.put(principal, accessToken);
    }

    @Override
    public void save(RefreshToken refreshToken) {
        InMemoryRefreshTokenImpl rt = (InMemoryRefreshTokenImpl)refreshToken;
        InMemoryAccessTokenImpl at = rt.getSourceAccessToken();
        OAuth2Principal principal = new OAuth2Principal(at.getClient(), at.getUser());
        this.refreshTokens.put(principal, refreshToken);
    }

    @Override
    public void deleteAccessToken(AccessToken accessToken) {
        if (!accessTokens.containsValue(accessToken)) {
            return;
        }

        OAuth2Principal principal = null;
        for (Map.Entry<OAuth2Principal, AccessToken> entry : accessTokens.entrySet()) {
            if (entry.getValue().equals(accessToken)) {
                principal = entry.getKey();
            }
        }
        if (principal != null) {
            accessTokens.remove(principal);
        }
    }

    @Override
    public void deleteAccessToken(RefreshToken refreshToken) {
        InMemoryRefreshTokenImpl rt = (InMemoryRefreshTokenImpl)refreshToken;
        InMemoryAccessTokenImpl at = rt.getSourceAccessToken();
        OAuth2Principal principal = new OAuth2Principal(at.getClient(), at.getUser());
        accessTokens.remove(principal);
    }

    @Override
    public void deleteRefreshToken(final RefreshToken refreshToken) {
        Set<OAuth2Principal> principals = new HashSet<>();
        for (Map.Entry<OAuth2Principal, RefreshToken> t : refreshTokens.entrySet()) {
            if (t.getValue().equals(refreshToken)) {
                principals.add(t.getKey());
            }
        }
        for (OAuth2Principal principal : principals) {
            refreshTokens.remove(principal);
        }
        
        for (Map.Entry<OAuth2Principal, AccessToken> t : accessTokens.entrySet()) {
            if (t.getValue().getRefreshTokenId() == null) {
                continue;
            }
            if (t.getValue().getRefreshTokenId().equals(refreshToken.getTokenId())) {
                ((InMemoryAccessTokenImpl)t.getValue()).setRefreshToken(null);
            }
        }
    }

}
