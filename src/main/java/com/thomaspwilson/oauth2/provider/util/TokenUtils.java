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