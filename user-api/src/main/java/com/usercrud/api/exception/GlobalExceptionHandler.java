package com.usercrud.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

/**
 * Global exception handler that maps exceptions to RFC 7807 Problem Details.
 *
 * <p>Satisfies NFR-OBS-002 (structured error responses).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle entity not found — returns 404.
     *
     * @param ex the exception
     * @return RFC 7807 problem detail
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(final EntityNotFoundException ex) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("https://api.usercrud.com/errors/not-found"));
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /**
     * Handle bean validation failures — returns 400.
     *
     * @param ex the exception
     * @return RFC 7807 problem detail
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(final MethodArgumentNotValidException ex) {
        final ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Validation failed");
        problem.setType(URI.create("https://api.usercrud.com/errors/validation"));
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("errors", ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList());
        return problem;
    }
}
