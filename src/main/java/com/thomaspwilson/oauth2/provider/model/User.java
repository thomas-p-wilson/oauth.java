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

import java.io.Serializable;

/**
 * A user represents an actor, typically human, which may be authenticated
 * through an external application in order to access protected resources.
 * {@link https://tools.ietf.org/html/rfc6749#section-4.3}
 */
public interface User extends Serializable {
    /**
     * Returns the unique identifier of the user.
     * @return The user's identifier
     */
    String getId();
    /**
     * Returns the user's (hopefully hashed (and salted)) password.
     * @return The user's password
     */
    String getPassword();

}