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

public abstract class OAuth2Exception extends RuntimeException {
    protected String errorUri;
    protected String state;

    //~ Constructions ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public OAuth2Exception() {}
    public OAuth2Exception(final String string) {
        super(string);
    }

    //~ OAuth-related ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public abstract String getErrorCode();
    public int getHttpStatusCode() {
        return 500;
    }
    
    public String getErrorDescription() {
        return getMessage();
    }
    public String getErrorUri() {
        return errorUri;
    }
    public void setErrorUri(final String errorUri) {
        this.errorUri = errorUri;
    }
    public String getState() {
        return state;
    }
    public void setState(final String state) {
        this.state = state;
    }
}