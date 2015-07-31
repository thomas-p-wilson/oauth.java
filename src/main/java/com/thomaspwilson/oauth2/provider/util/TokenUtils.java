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

import com.thomaspwilson.oauth2.provider.Configuration;
import com.thomaspwilson.oauth2.provider.model.AccessToken;
import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import org.joda.time.DateTime;

public class TokenUtils {

    public static boolean isExpired(final AccessToken token,
            final Configuration config, final Client client) {
        long lifespan = config.getDefaultAccessTokenLifespan();
        if (client.getAccessTokenLifespanSeconds() > 0) {
            lifespan = Math.min(lifespan, client.getAccessTokenLifespanSeconds());
        }
        
        DateTime maxAge = new DateTime()
                .minusSeconds((int)lifespan);
        return token.getIssueTime().isBefore(maxAge);
    }
    public static boolean isExpired(final RefreshToken token,
            final Configuration config, final Client client) {
        long lifespan = config.getDefaultRefreshTokenLifespan();
        if (client.getAccessTokenLifespanSeconds() > 0) {
            lifespan = Math.min(lifespan, client.getRefreshTokenLifespanSeconds());
        }
        
        DateTime maxAge = new DateTime()
                .minusSeconds((int)lifespan);
        return token.getIssueTime().isBefore(maxAge);
    }

}