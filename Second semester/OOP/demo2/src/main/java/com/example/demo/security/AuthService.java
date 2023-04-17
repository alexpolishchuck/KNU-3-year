package com.example.demo.security;

import com.example.demo.users.BankService;
import com.example.demo.users.Person;
import com.example.demo.users.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public AuthenticationResponse register( RegisterRequest request)
    {

        Person user = Person.builder()
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.USER)
                .build();

        bankService.saveUser(user);

       String token = jwtService.generateToken(user);

       return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(RegisterRequest request)
    {
        try
        {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getName(),
                            request.getPassword()
                    )
            );
        }
        catch (BadCredentialsException exception)
        {
            return new AuthenticationResponse();
        }

        Person user = (Person)bankService.loadUserByUsername(request.getName());

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    private final BankService bankService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
}
