package com.thomaspwilson.oauth2.provider.flows;

import com.thomaspwilson.oauth2.provider.AbstractTest;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceOwnerPasswordCredentialsFlowTest extends AbstractTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceOwnerPasswordCredentialsFlowTest.class);
    
    // - Ensure that non-confidential client can't use client credentials flow
    // - Ensure that confidential client can use client credentials flow
    // - Ensure that refresh token is NEVER provided
    
    
    @Test
    public void ensureCanBeSuccessful() {
        AccessToken at = accessTokenManager.request(request());
        assertThat(at.getClientId(), is(equalTo("client_a")));
        assertThat(at.getUserId(), is(equalTo("user_a")));
        assertThat(at.getRefreshTokenId(), is(nullValue()));
        assertThat(at.getTokenId(), is(notNullValue()));
        assertThat(at.getTokenType(), is(equalTo("bearer")));
        assertThat(new DateTime().getMillis() - at.getIssueTime().getMillis(), is(lessThan(1000L)));
    }
//    @Test
//    public void ensureNonConfidentialClientCannotUse() {
//        THROWN.expect(UnauthorizedClientException.class);
//        THROWN.expectMessage("Public client not authorized to use client_credentials grant type");
//        Request request = new MutableRequest(getCanonicalRequestMap())
//                .setClientId(CLIENT_C.getId())
//                .setClientSecret(CLIENT_C.getSecret());
//        AccessToken at = accessTokenManager.request(request);
//    }
    
    @Override
    protected String[] getRequiredFields() {
        return new String[]{
            "client_id", "client_secret","username","password"
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
            put("grant_type", Collections.singletonList("password"));
            put("username", Collections.singletonList(USER_A.getId()));
            put("password", Collections.singletonList(USER_A.getPassword()));
            put("scope", Collections.singletonList("profile"));
        }};
    }
    
}
