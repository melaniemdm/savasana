package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTests {

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        // Initialisation d'un objet SignupRequest pour les tests
        signupRequest = new SignupRequest();
    }

    @Test
    void testSetEmail() {
        // Arrange
        String email = "testuser@example.com";

        // Act
        signupRequest.setEmail(email);

        // Assert
        assertEquals(email, signupRequest.getEmail());
    }

    @Test
    void testSetFirstName() {
        // Arrange
        String firstName = "John";

        // Act
        signupRequest.setFirstName(firstName);

        // Assert
        assertEquals(firstName, signupRequest.getFirstName());
    }

    @Test
    void testSetLastName() {
        // Arrange
        String lastName = "Doe";

        // Act
        signupRequest.setLastName(lastName);

        // Assert
        assertEquals(lastName, signupRequest.getLastName());
    }

    @Test
    void testSetPassword() {
        // Arrange
        String password = "securePassword";

        // Act
        signupRequest.setPassword(password);

        // Assert
        assertEquals(password, signupRequest.getPassword());
    }

    @Test
    void testEmailNotBlank() {
        // Arrange
        signupRequest.setEmail("testuser@example.com");

        // Assert
        assertNotNull(signupRequest.getEmail());
        assertTrue(signupRequest.getEmail().length() > 0, "Email should not be blank");
    }

    @Test
    void testFirstNameNotBlank() {
        // Arrange
        signupRequest.setFirstName("John");

        // Assert
        assertNotNull(signupRequest.getFirstName());
        assertTrue(signupRequest.getFirstName().length() >= 3, "First name should have at least 3 characters");
    }

    @Test
    void testLastNameNotBlank() {
        // Arrange
        signupRequest.setLastName("Doe");

        // Assert
        assertNotNull(signupRequest.getLastName());
        assertTrue(signupRequest.getLastName().length() >= 3, "Last name should have at least 3 characters");
    }

    @Test
    void testPasswordNotBlank() {
        // Arrange
        signupRequest.setPassword("securePassword");

        // Assert
        assertNotNull(signupRequest.getPassword());
        assertTrue(signupRequest.getPassword().length() >= 6, "Password should have at least 6 characters");
    }

    @Test
    void testEmailFormat() {
        // Arrange
        signupRequest.setEmail("invalidemail.com");

        // Assert
        assertFalse(signupRequest.getEmail().contains("@"), "Email should contain '@' symbol");
    }

    @Test
    void testToString() {
        // Arrange
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("securePassword");

        // Act
        String result = signupRequest.toString();

        // Assert
        assertTrue(result.contains("testuser@example.com"));
        assertTrue(result.contains("John"));
        assertTrue(result.contains("Doe"));
        assertTrue(result.contains("securePassword"));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        signupRequest.setEmail("testuser@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");
        signupRequest.setPassword("securePassword");

        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setEmail("testuser@example.com");
        signupRequest2.setFirstName("John");
        signupRequest2.setLastName("Doe");
        signupRequest2.setPassword("securePassword");

        // Act & Assert
        assertEquals(signupRequest, signupRequest2); // Test égalité
        assertEquals(signupRequest.hashCode(), signupRequest2.hashCode()); // Test hashCode
    }
}
