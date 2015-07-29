/*
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
package com.thomaspwilson.oauth2.exception;

import com.thomaspwilson.oauth2.provider.exception.AccessDeniedException;
import com.thomaspwilson.oauth2.provider.exception.InvalidClientException;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.exception.InvalidScopeException;
import com.thomaspwilson.oauth2.provider.exception.OAuth2Exception;
import com.thomaspwilson.oauth2.provider.exception.ServerErrorException;
import com.thomaspwilson.oauth2.provider.exception.TemporarilyUnavailableException;
import com.thomaspwilson.oauth2.provider.exception.UnauthorizedClientException;
import com.thomaspwilson.oauth2.provider.exception.UnsupportedGrantTypeException;
import com.thomaspwilson.oauth2.provider.exception.UnsupportedResponseTypeException;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Tests all exceptions to make sure that the API isn't unexpectedly modified.
 */
@RunWith(Parameterized.class)
public class ExceptionTest {
    @Parameterized.Parameters
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {new AccessDeniedException(), "access_denied", null, 500},
            {new InvalidClientException(""), "invalid_client", "", 400},
            {new InvalidGrantException(""), "invalid_grant", "", 400},
            {new InvalidRequestException(""), "invalid_request", "", 500},
            {new InvalidScopeException(""), "invalid_scope", "", 500},
            {new ServerErrorException(), "server_error", null, 500},
            {new TemporarilyUnavailableException(), "temporarily_unavailable", null, 503},
            {new UnauthorizedClientException(""), "unauthorized_client", "", 500},
            {new UnsupportedGrantTypeException(), "unsupported_grant_type", null, 500},
            {new UnsupportedResponseTypeException(), "unsupported_response_type", null, 500}
        });
    }
    
    private final OAuth2Exception exception;
    private final String errorCode;
    private final String errorDescription;
    private final int statusCode;
    
    public ExceptionTest(final OAuth2Exception exception,
            final String errorCode,
            final String errorDescription,
            final int statusCode) {
        this.exception = exception;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.statusCode = statusCode;
    }
    
    @Test
    public void testException() {
        exception.setState("abc");
        exception.setErrorUri("/error");
        assertThat(exception.getState(), is(equalTo("abc")));
        assertThat(exception.getErrorCode(), is(equalTo(errorCode)));
        assertThat(exception.getErrorDescription(), is(equalTo(errorDescription)));
        assertThat(exception.getErrorUri(), is(equalTo("/error")));
        assertThat(exception.getHttpStatusCode(), is(equalTo(statusCode)));
        
    }
}