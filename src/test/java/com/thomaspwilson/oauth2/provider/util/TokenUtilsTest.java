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
package com.thomaspwilson.oauth2.provider.util;

import com.thomaspwilson.oauth2.fixtures.MutableConfiguration;
import com.thomaspwilson.oauth2.fixtures.TestClient;
import com.thomaspwilson.oauth2.provider.ClientType;
import com.thomaspwilson.oauth2.provider.Configuration;
import com.thomaspwilson.oauth2.provider.examples.memory.model.InMemoryAccessTokenImpl;
import com.thomaspwilson.oauth2.provider.examples.memory.model.InMemoryRefreshTokenImpl;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import static org.hamcrest.Matchers.is;
import org.joda.time.DateTime;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TokenUtilsTest {
    private static final TokenUtils UNDER_TEST = new TokenUtils();
    private static final Client FIVE_MIN_CLIENT = new TestClient("abc", "abc", ClientType.CONFIDENTIAL, 5 * 60, 5 * 60, null, null);
    private static final Client TEN_MIN_CLIENT = new TestClient("abc", "abc", ClientType.CONFIDENTIAL, 10 * 60, 10 * 60, null, null);
    
    @Test
    public void testNonExpiredAccessToken() {
        Configuration config = new MutableConfiguration()
                .setDefaultAccessTokenLifespan(10 * 60);
        AccessToken token = new InMemoryAccessTokenImpl("abc")
                .setIssueTime(new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, TEN_MIN_CLIENT), is(false));
    }
    
    @Test
    public void prefersSmallerOfTwoAccessTokenLifespans() {
        Configuration config = new MutableConfiguration()
                .setDefaultAccessTokenLifespan(10 * 60);
        AccessToken token = new InMemoryAccessTokenImpl("abc")
                .setIssueTime(new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, FIVE_MIN_CLIENT), is(true));
    }
    
    @Test
    public void prefersSmallerOfTwoAccessTokenLifespans2() {
        Configuration config = new MutableConfiguration()
                .setDefaultAccessTokenLifespan(5 * 60);
        AccessToken token = new InMemoryAccessTokenImpl("abc")
                .setIssueTime(new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, TEN_MIN_CLIENT), is(true));
    }
    
    @Test
    public void testNonExpiredRefreshToken() {
        Configuration config = new MutableConfiguration()
                .setDefaultRefreshTokenLifespan(10 * 60);
        RefreshToken token = new InMemoryRefreshTokenImpl("abc", null, new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, TEN_MIN_CLIENT), is(false));
    }
    
    @Test
    public void prefersSmallerOfTwoRefreshTokenLifespans() {
        Configuration config = new MutableConfiguration()
                .setDefaultRefreshTokenLifespan(10 * 60);
        RefreshToken token = new InMemoryRefreshTokenImpl("abc", null, new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, FIVE_MIN_CLIENT), is(true));
    }
    
    @Test
    public void prefersSmallerOfTwoRefreshTokenLifespans2() {
        Configuration config = new MutableConfiguration()
                .setDefaultRefreshTokenLifespan(5 * 60);
        RefreshToken token = new InMemoryRefreshTokenImpl("abc", null, new DateTime().minusMinutes(9));
        assertThat(TokenUtils.isExpired(token, config, TEN_MIN_CLIENT), is(true));
    }
    
}
