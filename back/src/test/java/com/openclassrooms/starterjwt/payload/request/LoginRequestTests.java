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
        String email = "testuser@example.com";
        loginRequest.setEmail(email);

        // Act
        String result = loginRequest.getEmail();

        // Assert
        assertNotNull(result, "Email should not be null");
        assertTrue(result.length() > 0, "Email should not be blank");
    }

    @Test
    void testPasswordNotBlank() {
        // Arrange
        String password = "securePassword";
        loginRequest.setPassword(password);

        // Act
        String result = loginRequest.getPassword();

        // Assert
        assertNotNull(result, "Password should not be null");
        assertTrue(result.length() > 0, "Password should not be blank");
    }
}

