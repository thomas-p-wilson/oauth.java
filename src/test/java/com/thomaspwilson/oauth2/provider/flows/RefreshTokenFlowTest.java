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
package com.thomaspwilson.oauth2.provider.flows;

import com.thomaspwilson.oauth2.provider.AbstractTest;
import static com.thomaspwilson.oauth2.provider.AbstractTest.CLIENT_A;
import com.thomaspwilson.oauth2.provider.examples.memory.model.InMemoryAccessTokenImpl;
import com.thomaspwilson.oauth2.provider.examples.memory.model.InMemoryRefreshTokenImpl;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefreshTokenFlowTest extends AbstractTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RefreshTokenFlowTest.class);
    private static final InMemoryAccessTokenImpl ACCESS_A = new InMemoryAccessTokenImpl("access_a")
            .setClient(CLIENT_A)
            .setIssueTime(new DateTime().minusMinutes(30))
            .setTokenType("bearer")
            .setScope(new HashSet<String>(){{
                add("name");
                add("email");
            }});
    private static final InMemoryRefreshTokenImpl REFRESH_A = new InMemoryRefreshTokenImpl("refresh_a", ACCESS_A, new DateTime().minusMinutes(30));
    
    @Before
    @Override
    public void before() throws Exception {
        ACCESS_A.setRefreshToken(REFRESH_A);
        TOKEN_SERVICE.save(ACCESS_A);
        TOKEN_SERVICE.save(REFRESH_A);
        super.before();
    }
    
    @Test
    public void ensureCanBeSuccessful() {
        AccessToken at = accessTokenManager.request(request());
        assertThat(at.getClientId(), is(equalTo("client_a")));
        assertThat(at.getUserId(), is(nullValue()));
        assertThat(at.getRefreshTokenId(), is(nullValue()));
        assertThat(at.getTokenId(), is(notNullValue()));
        assertThat(at.getTokenType(), is(equalTo("bearer")));
        assertThat(new DateTime().getMillis() - at.getIssueTime().getMillis(), is(lessThan(1000L)));
    }
    
    @Test
    public void refreshWithoutScopeShouldResultInOriginalScope() {
        
    }
    
    @Test
    public void refreshWithExcessiveScopeWithNarrowing() {
        
    }
    
    @Test
    public void refreshWithExcessiveScopeWithoutNarrowing() {
        
    }
    
    @Test
    @Override
    public void throwsErrorOnUnknownClient() {
        THROWN.expect(InvalidGrantException.class);
        THROWN.expectMessage("Invalid refresh token");
        Request request = request()
                .setClientId("unknown");
        accessTokenManager.request(request);
    }
    
    @Override
    protected String[] getRequiredFields() {
        return new String[]{
            "refresh_token"
        };
    }
    @Override
    protected Map<String, List<String>> getRequestHeaders() {
        return new HashMap<String, List<String>>(){{
            put("Authorization", Collections.singletonList("Basic "+base64(CLIENT_A)));
        }};
    }
    @Override
    protected Map<String, List<String>> getRequestParameters() {
        return new HashMap<String, List<String>>(){{
            put("grant_type", Collections.singletonList("refresh_token"));
            put("refresh_token", Collections.singletonList(REFRESH_A.getTokenId()));
            put("scope", Collections.singletonList("name email"));
        }};
    }
    
    
}
