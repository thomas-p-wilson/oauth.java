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
package com.thomaspwilson.oauth2.provider;

public interface Parameters {
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String GRANT_TYPE = "grant_type";
    String REFRESH_TOKEN = "refresh_token";
    String USERNAME = "username";
    String PASSWORD = "password";
    String RESPONSE_TYPE = "response_type";
    String CODE = "code";
    String REDIRECT_URI = "redirect_uri";
    String USER_ID = "user_id";
    String SCOPE = "scope";
}