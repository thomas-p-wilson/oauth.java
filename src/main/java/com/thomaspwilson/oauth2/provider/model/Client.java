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

import com.thomaspwilson.oauth2.provider.ClientType;
import java.io.Serializable;
import java.util.Set;

/**
 * A client represents an external application. The client or its users may
 * request authentication in order to access protected resources.
 * {@link https://tools.ietf.org/html/rfc6749#section-2}
 */
public interface Client extends Serializable {
    /**
     * Return the unique identifier of the client, pursuant to
     * {@link https://tools.ietf.org/html/rfc6749#section-2.2}
     * @return 
     */
    String getId();
    /**
     * Return the secret used to authenticate the client, pursuant to
     * {@link https://tools.ietf.org/html/rfc6749#section-2.3.1}
     * @return 
     */
    String getSecret();
    /**
     * Returns the type of the client, pursuant to 
     * {@link https://tools.ietf.org/html/rfc6749#section-2.1}
     * @return The client type
     */
    ClientType getType();
    /**
     * Retrieve the time, in seconds, that an access token is considered valid
     * post-issuance.
     * @return The access token lifespan
     */
    Integer getAccessTokenLifespanSeconds();
    /**
     * Retrieve the time, in seconds, that a refresh token is considered valid
     * post-issuance.
     * @return The refresh token lifespan
     */
    Integer getRefreshTokenLifespanSeconds();
    /**
     * Retrieve the grant types the client is permitted to use.
     * {@link https://tools.ietf.org/html/rfc6749#section-4}
     * @return The allowed grant types
     */
    Set<String> getAllowedGrantTypes();
    /**
     * Retrieve the scopes the client is permitted to access.
     * {@link https://tools.ietf.org/html/rfc6749#section-3.3}
     * @return The allowed scopes
     */
    Set<String> getAllowedScopes();
}