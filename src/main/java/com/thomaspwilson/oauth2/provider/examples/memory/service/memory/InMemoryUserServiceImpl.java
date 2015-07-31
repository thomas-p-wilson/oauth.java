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
package com.thomaspwilson.oauth2.provider.examples.memory.service.memory;

import com.thomaspwilson.oauth2.provider.model.User;
import com.thomaspwilson.oauth2.provider.service.UserService;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserServiceImpl implements UserService {
    public static final Map<String, User> USERS = new HashMap<>();
    
    public InMemoryUserServiceImpl(final User...users) {
        this(Arrays.asList(users));
    }
    public InMemoryUserServiceImpl(final Collection<User> users) {
        for (User user : users) {
            USERS.put(user.getId(), user);
        }
    }
    public InMemoryUserServiceImpl(final Map<String, User> users) {
        USERS.putAll(users);
    }

    @Override
    public User find(final String id) {
        if (!USERS.containsKey(id)) {
            return null;
        }
        return USERS.get(id);
    }

    @Override
    public User authenticate(final String id, final String password) {
        if (!USERS.containsKey(id)) {
            return null;
        }
        User user = USERS.get(id);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
}