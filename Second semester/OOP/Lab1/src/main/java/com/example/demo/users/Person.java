package com.example.demo.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Person{

    public Person(String name)
    {
        this.name = name;
    }
    public Person(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return name;
    }

    private int id;
    private String name;
    private String password;
    private Roles role;
}
