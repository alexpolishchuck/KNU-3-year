package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/authentication_callback",
                        "/registration_callback",
                        "/",
                        "/login",
                        "/logout",
                        "/styles/**",
                        "/register",
                        "/authorize",
                        "/on_main_page_loaded_script",
                        "/scripts/**")
                .permitAll()
                .antMatchers("/admin_page","/admin_page/unblock_card").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll();

        return http.build();
    }
    private LogoutSuccessHandler logoutSuccessHandler()
    {
       return new LogoutHandlerImpl(webConfig);
    }

    private final WebConfig webConfig;
}
