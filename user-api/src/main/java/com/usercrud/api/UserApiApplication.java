package com.usercrud.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point for the user-api service.
 *
 * <p>Bootstraps the Spring context, triggers Flyway migration,
 * validates JPA schema, and starts embedded Tomcat.
 * Satisfies FR-046, FR-047, FR-048, NFR-004.
 */
@SpringBootApplication
public class UserApiApplication {

    /**
     * Application entry point.
     *
     * @param args command-line arguments passed to the Spring application
     */
    public static void main(final String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
}
