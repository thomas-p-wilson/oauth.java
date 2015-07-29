package com.thomaspwilson.oauth2.provider;

public enum ClientType {
    PUBLIC("public"),
    CONFIDENTIAL("confidential");
    
    private final String name;
    
    private ClientType(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
