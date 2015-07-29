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
 * The <code>invalid_grant</code> error code indicates that the provided grant
 * or refresh token is invalid, expired, revoked, does not match the redirection
 * URI used in the authorization request, or was issued to another client.
 * 
 * {@link https://tools.ietf.org/html/rfc6749#section-5.2}
 */
public class InvalidGrantException extends OAuth2Exception {

    public InvalidGrantException(final String string) {
        super(string);
    }

    @Override
    public int getHttpStatusCode() {
        return 400;
    }
    @Override
    public String getErrorCode() {
        return "invalid_grant";
    }
    
}