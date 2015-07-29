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
package com.thomaspwilson.oauth2.provider.exception;

/**
 * The <code>invalid_scope</code> error code indicates that the requested scope
 * is invalid, unknown, malformed, or exceeds the scope granted by the resource
 * owner.
 * 
 * {@link https://tools.ietf.org/html/rfc6749#section-4.1.2.1}
 * {@link https://tools.ietf.org/html/rfc6749#section-4.2.2.1}
 * {@link https://tools.ietf.org/html/rfc6749#section-5.2}
 */
public class InvalidScopeException extends OAuth2Exception {

    public InvalidScopeException(final String string) {
        super(string);
    }

    @Override
    public String getErrorCode() {
        return "invalid_scope";
    }
    
}