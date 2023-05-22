package com.example.demo.security.passwordEncoders;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class PasswordEncoderSha256 {

    public static String encode(String password)
    {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
