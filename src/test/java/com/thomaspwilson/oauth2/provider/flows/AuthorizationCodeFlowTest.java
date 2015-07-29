package com.thomaspwilson.oauth2.provider.flows;

import com.thomaspwilson.oauth2.provider.AbstractTest;
import static com.thomaspwilson.oauth2.provider.AbstractTest.CLIENT_A;
import com.thomaspwilson.oauth2.provider.exception.InvalidGrantException;
import com.thomaspwilson.oauth2.provider.impl.memory.model.InMemoryAuthorizationCodeImpl;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import java.util.Arrays;
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
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class AuthorizationCodeFlowTest extends AbstractTest {
    private static final InMemoryAuthorizationCodeImpl CODE_A = new InMemoryAuthorizationCodeImpl("code_a")
            .setClientId(CLIENT_A.getId())
            .setRedirectUri("https://www.example.org/cb")
            .setScope(new HashSet<>(Arrays.asList("name", "email")));
    
    @Before
    @Override
    public void before() throws Exception {
        CODE_A.setIsRedeemed(false);
        CODE_SERVICE.save(CODE_A);
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
    public void returnsErrorOnRedirectUriMismatch() {
        THROWN.expect(InvalidGrantException.class);
        THROWN.expectMessage("Requested redirect_uri does not match redirect_uri given in authorization code request");
        
        accessTokenManager.request(request()
                .setRedirectUri("https://www.example.com/cb"));
    }
    @Test
    public void returnsErrorOnRequestWithRedeemedCode() {
        // XXX Must also invalidate all access tokens associated with the code
        
        assertThat(accessTokenManager.request(request()),
                is(notNullValue()));
        try {
            accessTokenManager.request(request());
            fail("Should have failed to redeem code a second time");
        } catch (InvalidGrantException ex) {
            assertThat(ex.getMessage(), is(equalTo("Invalid authorization code")));
        }
    }

    @Override
    public void throwsErrorOnExcessiveScope() { /* Does not apply */ }

    @Override
    protected String[] getRequiredFields() {
        return new String[]{"code","client_id"};
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
            put("grant_type", Collections.singletonList("authorization_code"));
            put("code", Collections.singletonList(CODE_A.getId()));
            put("redirect_uri", Collections.singletonList("https://www.example.org/cb"));
        }};
    }
    
}
