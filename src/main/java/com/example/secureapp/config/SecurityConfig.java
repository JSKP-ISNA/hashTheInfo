package com.example.secureapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig — Spring Boot 3.5.x / Spring Security 6.x
 */
@Configuration
public class SecurityConfig {

    

    /**
     * Backward-compatible PasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        
        PasswordEncoder delegating =
                org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(12);

        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return delegating.encode(rawPassword);
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (encodedPassword == null) return false;
                if (encodedPassword.startsWith("{")) {
                    return delegating.matches(rawPassword, encodedPassword);
                }
                return bcrypt.matches(rawPassword, encodedPassword);
            }
            @Override
            public boolean upgradeEncoding(String encodedPassword) {
                return encodedPassword != null && !encodedPassword.startsWith("{");
            }
        };
    }

    /**
     * Modern way to obtain AuthenticationManager without deprecated provider wiring.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    

    /**
     * CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration cfg = new CorsConfiguration();
    cfg.setAllowedOrigins(List.of("*"));
    cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
    cfg.setAllowedHeaders(List.of("*"));
    cfg.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    /**
     * Ignore static assets at the WebSecurity layer.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                "/favicon.ico",
                "/assets/**",
                "/images/**",
                "/css/**",
                "/js/**"
        );
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
    /**
     * Main HTTP security configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(c -> c.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))

            .headers(h -> h.frameOptions(f -> f.disable()))

            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/login",
                        "/register",
                        "/h2-console/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler())
                .failureUrl("/login?error")
                .permitAll()
            )

            .logout(l -> l
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED
                )
            );

        return http.build();
    }
}
