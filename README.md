# modernize_app_modernized

Modernized User CRUD REST API — Spring Boot 3.x microservice with React frontend.

## Overview

This project is the result of modernizing a legacy Java Servlet/JSP monolith into independently deployable microservices. Key changes include:

- **Backend**: Spring Boot 3.x REST API with Spring Data JPA, Flyway migrations, Spring Security 6 + OAuth2/JWT
- **Frontend**: React 18 + TypeScript SPA consuming versioned REST/JSON APIs
- **Observability**: Structured JSON logging (Logstash Logback Encoder), Spring Boot Actuator
- **Testing**: JUnit 5, Mockito, @DataJpaTest, @WebMvcTest with JaCoCo coverage enforcement (>=80%)
- **Database**: PostgreSQL (production), H2 (test)

## Structure

```
user-api/                  # Spring Boot backend service
  src/main/java/           # Application source code
  src/main/resources/      # Configuration and Flyway migrations
  src/test/java/           # Unit and integration tests
  pom.xml                  # Maven build descriptor
docs/                      # Design documents and code review
```

## Getting Started

```bash
cd user-api
mvn clean verify
mvn spring-boot:run
```
