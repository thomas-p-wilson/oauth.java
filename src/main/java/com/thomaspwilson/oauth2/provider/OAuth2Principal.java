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
/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.thomaspwilson.oauth2.provider;

import com.thomaspwilson.oauth2.provider.model.Client;
import com.thomaspwilson.oauth2.provider.model.User;
import java.io.Serializable;
import java.security.Principal;

/**
 * The OAuth2Principal identifies the authenticated actor as a combination of a
 * client and optional user.
 */
public class OAuth2Principal implements Principal, Serializable {

    private final Client client;
    private final User user;

    //~ Construction ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public OAuth2Principal(final Client client, final User user) {
        this.client = client;
        this.user = user;
    }

    @Override
    public String getName() {
        if (user != null) {
            return user.getId();
        }
        return client.getId();
    }

    public Client getClient() {
        return client;
    }

    public User getUser() {
        return user;
    }

    public boolean isClientOnly() {
        return this.user == null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OAuth2Principal)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }

        OAuth2Principal other = (OAuth2Principal) obj;

        return !(user != null ? !user.equals(other.user)
                : other.user != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (client != null ? client.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

}
