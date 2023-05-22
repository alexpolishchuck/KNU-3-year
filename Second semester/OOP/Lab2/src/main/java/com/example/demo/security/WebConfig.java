package com.example.demo.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebConfig {
    @Value(value = "${com.auth0.domain}")
    public String domain;

    @Value(value = "${com.auth0.clientId}")
    public String clientId;

    @Value(value = "${com.auth0.clientSecret}")
    public String clientSecret;
}