package com.openclassrooms.starterjwt.payload.request;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTests {

    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // Initialisation d'un objet LoginRequest pour les tests
        loginRequest = new LoginRequest();
    }

    @Test
    void testSetEmail() {
        // Arrange
        String email = "testuser@example.com";

        // Act
        loginRequest.setEmail(email);

        // Assert
        assertEquals(email, loginRequest.getEmail());
    }

    @Test
    void testSetPassword() {
        // Arrange
        String password = "securePassword";

        // Act
        loginRequest.setPassword(password);

        // Assert
        assertEquals(password, loginRequest.getPassword());
    }

    @Test
    void testEmailNotBlank() {
        // Arrange
        loginRequest.setEmail("testuser@example.com");

        // Assert
        assertNotNull(loginRequest.getEmail());
        assertTrue(loginRequest.getEmail().length() > 0, "Email should not be blank");
    }

    @Test
    void testPasswordNotBlank() {
        // Arrange
        loginRequest.setPassword("securePassword");

        // Assert
        assertNotNull(loginRequest.getPassword());
        assertTrue(loginRequest.getPassword().length() > 0, "Password should not be blank");
    }
}

