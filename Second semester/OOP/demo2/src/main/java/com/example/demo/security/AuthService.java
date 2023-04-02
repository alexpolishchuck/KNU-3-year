package com.example.demo.security;

import com.example.demo.users.BankService;
import com.example.demo.users.Person;
import com.example.demo.users.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {



    public AuthenticationResponse register( RegisterRequest request)
    {
        Person user = Person
                .builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.USER)
                .build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request)
    {

    }

    private final BankService bankService;
    private final PasswordEncoder passwordEncoder;
}
