package com.thomaspwilson.oauth2.fixtures;

import com.thomaspwilson.oauth2.provider.model.User;

public class TestUser implements User {
    
    private final String id;
    private final String password;
    
    public TestUser(final String id, final String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }
    
}