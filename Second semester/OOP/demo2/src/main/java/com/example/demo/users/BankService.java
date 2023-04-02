package com.example.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankService implements UserDetailsService {

    @Autowired
    public BankService(BankRepository repository)
    {
        this.repository = repository;
    }
    private BankRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username);
    }

    public UserDetails loadByUsername(String username)
    {
    }
}
