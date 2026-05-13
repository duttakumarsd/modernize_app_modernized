package com.usercrud.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 6 configuration.
 *
 * <p>Configures stateless JWT-based OAuth2 resource server.
 * Uses {@code /*} wildcard (not URI template variables) for path matching
 * to avoid the Spring Security 6 literal-match pitfall.
 * Satisfies NFR-SEC-001, NFR-SEC-002.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to configure
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Actuator health endpoint — public
                .requestMatchers("/actuator/health").permitAll()
                // Read access for authenticated users
                .requestMatchers(HttpMethod.GET, "/api/v1/users").authenticated()
                // Fixed: use /* instead of /{id} — Spring Security 6 does not treat {id} as wildcard
                .requestMatchers(HttpMethod.GET, "/api/v1/users/*").authenticated()
                // Write access requires ADMIN role
                .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/*").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));
        return http.build();
    }
}
