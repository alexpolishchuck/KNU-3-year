package com.example.demo.security;

import com.example.demo.security.passwordEncoders.PasswordEncoderSha256;
import com.example.demo.users.BankService;
import com.example.demo.users.Person;
import com.example.demo.users.Roles;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthService {
    public AuthenticationResponse register(RegisterRequest request)
    {

        Person user = Person.builder()
                .name(request.getName())
                .password(PasswordEncoderSha256.encode(request.getPassword()))
                .role(Roles.USER)
                .build();

        bankService.saveUser(user);

       String token = jwtService.generateToken(user);

       return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(RegisterRequest request)
    {
        Person user = bankService.loadUserByUsername(request.getName());

        if(user == null
                || !PasswordEncoderSha256.encode(request.getPassword()).equals(user.getPassword()))
            return new AuthenticationResponse();

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    private final BankService bankService;
    private final JwtService jwtService;
}
