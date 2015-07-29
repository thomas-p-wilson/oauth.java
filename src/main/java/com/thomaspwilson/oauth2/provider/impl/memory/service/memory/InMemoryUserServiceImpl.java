package com.thomaspwilson.oauth2.provider.impl.memory.service.memory;

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