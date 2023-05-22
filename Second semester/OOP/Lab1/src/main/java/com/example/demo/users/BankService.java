package com.example.demo.users;

import lombok.RequiredArgsConstructor;
import java.sql.SQLException;

@RequiredArgsConstructor
public class BankService{

    public Person loadUserByUsername(String username) {
        return repository.findByUsername(username);
    }
    public void saveUser(Person user) {
        Person p = (Person) loadUserByUsername(user.getUsername());
        if(loadUserByUsername(user.getUsername()) != null)
            return;
        try {
            repository.save(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final BankRepository repository;
}
