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
package com.thomaspwilson.oauth2.provider.model;

import com.thomaspwilson.oauth2.provider.io.Request;
import java.util.Set;

/**
 * The AccessToken is provided to external or third-party applications
 * representing authorization to access certain protected resources.
 * {@link https://tools.ietf.org/html/rfc6749#section-1.4}
 */
public interface AccessToken extends Token {
    
    public static final String TYPE_BEARER = "Bearer";
    public static final String TYPE_OAUTH = "OAuth2";
    
    public static String ACCESS_TOKEN = "access_token";
    public static String TOKEN_TYPE = "token_type";
    public static String EXPIRES_IN = "expires_in";
    public static String REFRESH_TOKEN = "refresh_token";
    public static String SCOPE = "scope";
    
    /**
     * The token type determines how the client is to use the token to access
     * protected resources.
     * {@link https://tools.ietf.org/html/rfc6749#section-7.1}
     * @return The token type
     */
    String getTokenType();
    /**
     * The scope determines the breadth of protected resources available to
     * the client using the access token.
     * {@link https://tools.ietf.org/html/rfc6749#section-3.3}
     * @return The scope
     */
    Set<String> getScope();
    /**
     * Retrieves the id of the refresh token associated with this access token,
     * if applicable.
     * @return The refresh token id
     */
    String getRefreshTokenId();
    /**
     * Retrieves the id of the authorization code that produced this access
     * token, if applicable.
     * @return The auth code id
     */
    String getAuthorizationCodeId();
    /**
     * Retrieves the id of the client to whom the token belongs.
     * @return The client id
     */
    String getClientId();
    /**
     * Retrieves the id of the user to whom the token belongs, if applicable.
     * @return The user id
     */
    String getUserId();
    /**
     * Retrieves the request that was used to generate this token.
     * @return The original request
     */
    Request getOriginalRequest();
}