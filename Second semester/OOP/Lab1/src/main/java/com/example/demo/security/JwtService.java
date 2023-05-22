package com.example.demo.security;

import com.example.demo.users.Person;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtService {
    public String extractUsername(String jwt)
    {
        return extractClaim(jwt, Claims::getSubject);
    }

    public String generateToken(Person userDetails)
    {
       return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String token)
    {
        return (!isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    public String generateToken(
            Map<String, Object> claims,
            Person userDetails)
    {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 600))
                .signWith(getSigningKey(), SignatureAlgorithm.HS384)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        return key;
    }

    private static final String SecretKey = "3777217A25432646294A404E635266556A586E3272357538782F413F4428472D";
}
