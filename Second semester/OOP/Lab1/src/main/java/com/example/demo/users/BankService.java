package com.example.demo.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@RequiredArgsConstructor
@Service
public class BankService implements UserDetailsService {
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
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
