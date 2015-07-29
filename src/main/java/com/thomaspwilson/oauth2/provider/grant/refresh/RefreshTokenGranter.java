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
package com.thomaspwilson.oauth2.provider.grant.refresh;

import com.thomaspwilson.oauth2.provider.AccessTokenManager;
import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.OAuth2Principal;
import com.thomaspwilson.oauth2.provider.Parameters;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.exception.InvalidScopeException;
import com.thomaspwilson.oauth2.provider.grant.AbstractTokenGranter;
import com.thomaspwilson.oauth2.provider.io.MutableRequest;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import com.thomaspwilson.oauth2.provider.model.User;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import com.thomaspwilson.oauth2.provider.service.UserService;
import com.thomaspwilson.oauth2.provider.util.StringUtils;
import com.thomaspwilson.oauth2.provider.util.TokenUtils;
import java.util.HashSet;
import java.util.Set;

public class RefreshTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "refresh_token";
    
    private final UserService userService;
    private final TokenService tokenService;

    public RefreshTokenGranter(final AccessTokenManager tokenManager) {
        super(tokenManager);
        this.userService = tokenManager.getUserService();
        this.tokenService = tokenManager.getTokenService();
    }

    @Override
    public AccessToken grant(Request request) {
        if (!grantType().equals(request.getGrantType())) {
            return null;
        }
        if (!tokenManager.getConfig().allowRefreshTokens()) {
            throw new InvalidGrantException("Unsupported grant type refresh_token");
        }
        
        validateRequest(request);
        
        RefreshToken refreshToken = tokenService.findRefreshToken(request.getRefreshToken());
        if (refreshToken == null) {
            throw new InvalidGrantException("Invalid refresh token: " + request.getRefreshToken());
        }
        
        AccessToken at = tokenService.findAccessToken(refreshToken.getSourceAccessTokenId());
        Client c = clientService.find(at.getClientId());
        User u = userService.find(at.getUserId());
        validatePrincipal(request, refreshToken, at, c, u);
        OAuth2Principal principal = new OAuth2Principal(c, u);
        
        // Validate the requested scope. Cannot be wider than the scope of the
        // original access token
        // https://tools.ietf.org/html/rfc6749#section-6
        request = new MutableRequest(request);
        validateScope(request, at);
        

        // clear out any access tokens already associated with the refresh
        // token.
        tokenService.deleteAccessToken(refreshToken);

        if (TokenUtils.isExpired(refreshToken, tokenManager.getConfig(), c)) {
            tokenService.deleteRefreshToken(refreshToken);
            throw new InvalidGrantException("Invalid refresh token: " + refreshToken.getTokenId());
        }
        
        if (!tokenManager.getConfig().allowRefreshTokenReuse()) {
            tokenService.deleteRefreshToken(refreshToken);
        }

        AccessToken accessToken = tokenService.createAccessToken(request, principal);
        tokenService.save(accessToken);
        return accessToken;
    }

    @Override
    protected void validateRequest(final Request request) {
        if (StringUtils.isBlank(request.getRefreshToken())) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.REFRESH_TOKEN);
        }
    }
    
    protected void validatePrincipal(final Request request,
            final RefreshToken refreshToken,
            final AccessToken sourceAccessToken,
            final Client client, final User user) {
        
        if (StringUtils.isBlank(sourceAccessToken.getClientId())) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshToken.getTokenId());
        }
        if (!StringUtils.isBlank(request.getClientId())
                && !sourceAccessToken.getClientId().equals(request.getClientId())) {
            throw new InvalidGrantException("Invalid refresh token: " + refreshToken.getTokenId());
        }
        if (!StringUtils.isBlank(request.getClientSecret()) && authenticateClient(request) != null) {
            return;
        }
        if (client.getType().equals(ClientType.CONFIDENTIAL)) {
            throw new InvalidRequestException("Missing or empty field "+Parameters.CLIENT_SECRET);
        }
    }

    protected Client authenticateClient(final Request request, final RefreshToken token) {
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
    
    protected void validateScope(final Request request, final AccessToken at) {
        if (request.getScope() == null || request.getScope().isEmpty()) {
            ((MutableRequest)request).setScope(at.getScope());
            return;
        }
        
        Set<String> disallowed = new HashSet<>(request.getScope());
        disallowed.removeAll(at.getScope());
        if (!disallowed.isEmpty()) {
            throw new InvalidScopeException("Client is not authorized for scope "+disallowed);
        }
    }
    
    

//    public AccessToken refreshAccessToken(String refreshTokenValue, Request request) {
//
//
//        RefreshToken refreshToken = tokenService.findRefreshToken(refreshTokenValue);
//        if (refreshToken == null) {
//            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
//        }
//
//        AccessToken at = tokenService.findAccessToken(refreshToken.getSourceAccessTokenId());
//        Client c = clientService.find(at.getClientId());
//        User u = userService.find(at.getUserId());
//        OAuth2Principal authentication = new OAuth2Principal(c, u);
//        if (at.getClientId() == null || !at.getClientId().equals(request.getClientId())) {
//            throw new InvalidGrantException("Invalid refresh token: " + refreshTokenValue);
//        }
//
//        // clear out any access tokens already associated with the refresh
//        // token.
//        tokenService.deleteAccessToken(refreshToken);
//
//        if (TokenUtils.isExpired(refreshToken, config, c)) {
//            tokenService.deleteRefreshToken(refreshToken);
//            throw new InvalidGrantException("Invalid refresh token: " + refreshToken.getTokenId());
//        }
//
//        Request narrowed = new MutableRequest(request).narrowScope(request.getScope());
//
//        if (!config.allowRefreshTokenReuse()) {
//            tokenService.deleteRefreshToken(refreshToken);
////            refreshToken = tokenService.createRefreshToken(authentication);
//        }
//
////        AccessToken accessToken = tokenService.createAccessToken(authentication, refreshToken);
////        tokenService.save(accessToken);
////        if (!config.allowRefreshTokenReuse()) {
////            tokenService.save(refreshToken);
////        }
////        return accessToken;
//        return null;
//    }

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }

}
