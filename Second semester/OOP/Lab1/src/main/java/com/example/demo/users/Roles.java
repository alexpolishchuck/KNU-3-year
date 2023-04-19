package com.example.demo.users;

public enum Roles {
    ADMIN("ADMIN"),
    USER("USER");
    Roles(String name)
    {
        this.name = name;
    }
    private String name;
}
