package com.example.testchatgpt.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    RENTER(1, "RENTER"),
    OWNER(2,"OWNER"),
    ADMIN(3, "ADMIN"),
    USER(4, "USER");

    private int id;
    private String name;

    Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
