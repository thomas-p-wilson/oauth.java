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
package com.thomaspwilson.oauth2.provider.service;

import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import com.thomaspwilson.oauth2.provider.OAuth2Principal;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AuthorizationCode;

/**
 * A TokenService implementation is responsible for managing most aspects of the
 * token lifecycle. A TokenService may implement any security features required
 * by the application, such as expiration for example.
 * @param <A> The AccessToken type served by the implementation
 * @param <R> The RefreshToken type served by the implementation
 */
public interface TokenService<A extends AccessToken, R extends RefreshToken> {
    A createAccessToken(Request request, OAuth2Principal principal);
    A createAccessToken(Request request, OAuth2Principal principal, AuthorizationCode code);
    R createRefreshToken(AccessToken sourceAccessToken);
    
    A findAccessToken(String value);
    A findAccessToken(OAuth2Principal principal);
    R findRefreshToken(String value);
//    OAuth2Principal findPrincipalForAccessToken(A accessToken);
//    OAuth2Principal findPrincipalForRefreshToken(R refreshToken);
    
    void save(A accessToken);
    void save(R accessToken);
    
    void deleteAccessToken(A accessToken);
    void deleteAccessToken(R refreshToken);
    void deleteRefreshToken(R refreshToken);
}