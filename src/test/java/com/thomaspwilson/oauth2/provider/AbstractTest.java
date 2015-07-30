package com.thomaspwilson.oauth2.provider;

import com.thomaspwilson.oauth2.fixtures.MutableConfiguration;
import com.thomaspwilson.oauth2.fixtures.TestClient;
import com.thomaspwilson.oauth2.fixtures.TestUser;
import com.thomaspwilson.oauth2.provider.examples.memory.service.memory.InMemoryClientServiceImpl;
import com.thomaspwilson.oauth2.provider.examples.memory.service.memory.InMemoryCodeServiceImpl;
import com.thomaspwilson.oauth2.provider.examples.memory.service.memory.InMemoryTokenServiceImpl;
import com.thomaspwilson.oauth2.provider.examples.memory.service.memory.InMemoryUserServiceImpl;
import com.thomaspwilson.oauth2.provider.exception.InvalidClientException;
import com.thomaspwilson.oauth2.provider.exception.InvalidRequestException;
import com.thomaspwilson.oauth2.provider.exception.InvalidScopeException;
import com.thomaspwilson.oauth2.provider.io.MutableRequest;
import com.thomaspwilson.oauth2.provider.io.Request;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.User;
import com.thomaspwilson.oauth2.provider.service.ClientService;
import com.thomaspwilson.oauth2.provider.service.CodeService;
import com.thomaspwilson.oauth2.provider.service.TokenService;
import com.thomaspwilson.oauth2.provider.service.UserService;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class AbstractTest {
    
    @Rule
    public final ExpectedException THROWN = ExpectedException.none();
    
    public static final Client CLIENT_A = new TestClient("client_a", "abc", ClientType.CONFIDENTIAL, 20 * 60 * 1000, 2 * 60 * 60 * 1000, new HashSet(Arrays.asList("client_credentials","password","refresh_token")), Collections.singleton("profile"));
    public static final Client CLIENT_B = new TestClient("client_b", "bcd", ClientType.CONFIDENTIAL, 20 * 60 * 1000, 2 * 60 * 60 * 1000, Collections.singleton("authorization_code"), Collections.singleton("name"));
    public static final Client CLIENT_C = new TestClient("client_c", "cde", ClientType.PUBLIC, 20 * 60 * 1000, 2 * 60 * 60 * 1000, Collections.singleton("authorization_code"), Collections.singleton("name"));
    
    public static final User USER_A = new TestUser("user_a", "abc");
    public static final User USER_B = new TestUser("user_b", "bcd");
    
    protected static final TokenService TOKEN_SERVICE = new InMemoryTokenServiceImpl();
    protected static final CodeService CODE_SERVICE = new InMemoryCodeServiceImpl();
    protected static final UserService USER_SERVICE = new InMemoryUserServiceImpl(USER_A, USER_B);
    protected static final ClientService CLIENT_SERVICE = new InMemoryClientServiceImpl(CLIENT_A, CLIENT_B, CLIENT_C);
    protected static final Configuration CONFIG = new MutableConfiguration()
            .setTokenService(TOKEN_SERVICE)
            .setCodeService(CODE_SERVICE)
            .setUserService(USER_SERVICE)
            .setClientService(CLIENT_SERVICE);
    protected static AccessTokenManager accessTokenManager;
    
    @Before
    public void before() throws Exception {
        accessTokenManager = new AccessTokenManager(CONFIG);
    }
    
    protected abstract String[] getRequiredFields();
    protected abstract Map<String, List<String>> getRequestHeaders();
    protected abstract Map<String, List<String>> getRequestParameters();
    protected MutableRequest request() {
        return new MutableRequest(getRequestHeaders(), getRequestParameters());
    }
    
    //~ General grant flow tests ~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Test
    public void throwsErrorOnMissingRequiredParameters() {
        for (String field : getRequiredFields()) {
            Map<String, List<String>> req = getRequestParameters();
            req.remove(field);
            MutableRequest request = new MutableRequest(getRequestHeaders(), req);
            if ("client_id".equals(field)) {
                request.setClientId(null);
            }
            if ("client_secret".equals(field)) {
                request.setClientSecret(null);
            }
            try {
                accessTokenManager.request(request);
                fail("Should have received invalid_request error for missing "+field+" field");
            } catch (InvalidRequestException ex) {
                assertThat(ex.getMessage(), is(equalTo("Missing or empty field "+field)));
            }
        }
    }
    @Test
    public void throwsErrorOnUnknownClient() {
        THROWN.expect(InvalidClientException.class);
        THROWN.expectMessage("");
        Request request = request()
                .setClientId("unknown");
        accessTokenManager.request(request);
    }
    @Test
    public void throwsErrorOnExcessiveScope() {
        THROWN.expect(InvalidScopeException.class);
        THROWN.expectMessage("");
        Request request = request()
                .setScope(Arrays.asList("name email profile"));
        accessTokenManager.request(request);
    }
    
    protected String base64(final Client client) {
        return DatatypeConverter.printBase64Binary(
                (client.getId() + ":" + client.getSecret()).getBytes(StandardCharsets.UTF_8)
        );
    }
}