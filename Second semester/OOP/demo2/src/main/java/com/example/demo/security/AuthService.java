package com.example.demo.security;

import com.auth0.AuthenticationController;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    public AuthenticationResponse register( RegisterRequest request)
    {
        return new AuthenticationResponse();
    }

    public AuthenticationResponse authenticate(RegisterRequest request)
    {
        return new AuthenticationResponse();
    }
    public AuthenticationController createAuthenticationController() {
        // JwkProvider required for RS256 tokens. If using HS256, do not use.
        JwkProvider jwkProvider = new JwkProviderBuilder(webConfig.domain).build();
        return AuthenticationController.newBuilder(webConfig.domain,
                        webConfig.clientId,
                        webConfig.clientSecret)
                .withJwkProvider(jwkProvider)
                .build();
    }

    private final WebConfig webConfig;
}
