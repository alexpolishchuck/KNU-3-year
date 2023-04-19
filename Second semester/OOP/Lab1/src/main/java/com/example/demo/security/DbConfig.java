package com.example.demo.security;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DbConfig {
    public String db_url = "jdbc:mysql://127.0.0.1:3306/root?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
    public String username = "root";
    public String password = "1234";
}
