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

package com.thomaspwilson.oauth2.provider.impl.memory.model;

import com.thomaspwilson.oauth2.provider.model.RefreshToken;
import org.joda.time.DateTime;

public class InMemoryRefreshTokenImpl implements RefreshToken {
    private final String id;
    private final InMemoryAccessTokenImpl sourceAccessToken;
    private final DateTime issueTime;
    
    public InMemoryRefreshTokenImpl(final String id,
            final InMemoryAccessTokenImpl sourceAccessToken,
            final DateTime issueTime) {
        this.id = id;
        this.sourceAccessToken = sourceAccessToken;
        this.issueTime = issueTime;
    }

    public InMemoryAccessTokenImpl getSourceAccessToken() {
        return sourceAccessToken;
    }
    @Override
    public String getSourceAccessTokenId() {
        return sourceAccessToken.getTokenId();
    }

    @Override
    public String getTokenId() {
        return id;
    }

    @Override
    public DateTime getIssueTime() {
        return issueTime;
    }
    
}