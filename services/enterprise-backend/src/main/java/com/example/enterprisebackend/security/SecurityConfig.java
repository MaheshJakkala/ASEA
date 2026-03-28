package com.example.enterprisebackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.disable())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/api/auth/**").permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                    // Project API
                    .requestMatchers(HttpMethod.POST, "/api/projects").hasAuthority("ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/projects/**").hasAuthority("ROLE_USER")
                    .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasAuthority("ROLE_USER")
                    .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasAuthority("ROLE_ADMIN")
                    // Issue API
                    .requestMatchers(HttpMethod.POST, "/api/issues").hasAuthority("ROLE_USER")
                    .requestMatchers(HttpMethod.GET, "/api/issues/**").hasAuthority("ROLE_USER")
                    .requestMatchers(HttpMethod.PATCH, "/api/issues/**").hasAuthority("ROLE_USER")
                    // User API
                    .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                    .anyRequest().authenticated()
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
