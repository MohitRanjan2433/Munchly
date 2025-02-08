package com.example.munchly.config;

import com.example.munchly.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private static final String[] PUBLIC_ROUTES = {
            "/auth/**"
    };

    private static final String[] AUTHENTICATED_ROUTES = {
            "/restaurants/**", "/menu/**"
    };

    private static final String[] ADMIN_ROUTES = {
            "/restaurants/create",  "/restaurants/update",  "/restaurants/delete"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (adjust as needed)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(PUBLIC_ROUTES).permitAll()
                                .requestMatchers(ADMIN_ROUTES).hasRole("ADMIN")
                        .requestMatchers(AUTHENTICATED_ROUTES).authenticated()
                                .anyRequest().authenticated() // Secure other endpoints
                )
                .sessionManagement(sessionConfig -> sessionConfig
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Add the JWT filter
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

