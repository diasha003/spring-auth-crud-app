package com.example.demo.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.PrintWriter;
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
    @Order(2)
    public SecurityFilterChain UiWebSecurityConfiguration (HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/user/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                ).formLogin(Customizer.withDefaults());


        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain ApiWebSecurityConfiguration (HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/**")
                        .hasAnyRole("ADMIN", "GUEST")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .exceptionHandling( e -> e.authenticationEntryPoint((req, res, exp) -> {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    PrintWriter writer = res.getWriter();
                    writer.println("{\"error\": " + exp.getMessage() + " }");
                }))
                .csrf(csrf -> csrf.disable())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );


        return http.build();
    }
}
