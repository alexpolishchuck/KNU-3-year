package com.example.demo.utils;

import com.auth0.jwt.interfaces.Claim;
import com.example.demo.security.TokenAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthenticationUtils {

    public static Map<String, Claim> get_claims_of_user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof TokenAuthentication
                && authentication.isAuthenticated()) {
            TokenAuthentication tokenAuthentication =
                    (TokenAuthentication) authentication;

            return tokenAuthentication.getClaims();
        }

        return new HashMap<>();
    }

    public static List<String> get_roles_of_user() {
        Map<String, Claim> claims = get_claims_of_user();
        Claim roles = claims.get("https://access_control/roles");

        if(roles != null)
            return claims.get("https://access_control/roles").asList(String.class);

        return new ArrayList<>();
    }

    public static String get_username() {
        Map<String, Claim> claims = get_claims_of_user();

        Claim name = claims.get("name");

        if(name != null)
            return name.asString();

        return "";
    }
}