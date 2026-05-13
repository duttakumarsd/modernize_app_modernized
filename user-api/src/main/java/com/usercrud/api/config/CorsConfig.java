package com.usercrud.api.config;

/**
 * CORS configuration placeholder.
 *
 * <p>CORS rules are currently managed at the API Gateway / ingress layer.
 * If service-level CORS is required in future, implement a
 * {@link org.springframework.web.servlet.config.annotation.WebMvcConfigurer}
 * bean here and register allowed origins via application properties.
 *
 * <p>Satisfies NFR-SEC-003 (cross-origin policy documented).
 */
public final class CorsConfig {

    /** Utility class — do not instantiate. */
    private CorsConfig() {
    }
}
