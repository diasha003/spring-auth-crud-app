package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.net.http.HttpRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth,
                              UserAuthService userAuthService,
                              PasswordEncoder passwordencoder) throws Exception{

        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordencoder.encode("password"))
                .roles("ADMIN");


        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userAuthService);
        provider.setPasswordEncoder(passwordencoder);
        auth.authenticationProvider(provider);
    }



    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{
        
    }
}
