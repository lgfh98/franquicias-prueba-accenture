package org.example.franchises.infrastructure.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleNoSuchElementException() {
        // Arrange
        NoSuchElementException ex = new NoSuchElementException("Franchise not found");

        // Act
        ProblemDetail problem = handler.handleNotFound(ex);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), problem.getStatus());
        assertEquals("Resource Not Found", problem.getTitle());
        assertEquals("Franchise not found", problem.getDetail());
        assertNotNull(problem.getProperties().get("timestamp"));
    }

    @Test
    void shouldHandleIllegalArgumentException() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("Invalid stock value");

        // Act
        ProblemDetail problem = handler.handleBadRequest(ex);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), problem.getStatus());
        assertEquals("Bad Request", problem.getTitle());
        assertEquals("Invalid stock value", problem.getDetail());
    }

    @Test
    void shouldHandleIllegalStateException() {
        // Arrange
        IllegalStateException ex = new IllegalStateException("Franchise already has active branches");

        // Act
        ProblemDetail problem = handler.handleConflict(ex);

        // Assert
        assertEquals(HttpStatus.CONFLICT.value(), problem.getStatus());
        assertEquals("Business Rule Conflict", problem.getTitle());
        assertEquals("Franchise already has active branches", problem.getDetail());
    }

    @Test
    void shouldHandleGeneralException() {
        // Arrange
        RuntimeException ex = new RuntimeException("Unexpected error");

        // Act
        ProblemDetail problem = handler.handleGeneralError(ex);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), problem.getStatus());
        assertEquals("Internal Server Error", problem.getTitle());
        assertEquals("An unexpected internal error occurred", problem.getDetail());
    }
}
