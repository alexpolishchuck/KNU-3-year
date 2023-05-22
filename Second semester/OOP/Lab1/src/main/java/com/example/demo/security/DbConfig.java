package com.example.demo.security;

public class DbConfig {

    public static String getDb_url() {
        return db_url;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    private static String db_url = "jdbc:mysql://127.0.0.1:3306/root?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false";
    private static String username = "root";
    private static String password = "1234";
}
