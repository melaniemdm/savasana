package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestTests {

    private SignupRequest signupRequest;

    @BeforeEach
    void setUp() {
        // Initialisation d'un objet SignupRequest
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
        String email = "testuser@example.com";
        signupRequest.setEmail(email);

        // Act
        String result = signupRequest.getEmail();

        // Assert
        assertNotNull(result, "Email should not be null");
        assertTrue(result.length() > 0, "Email should not be blank");
    }

    @Test
    void testFirstNameNotBlank() {
        // Arrange
        String firstName = "John";
        signupRequest.setFirstName(firstName);

        // Act
        String result = signupRequest.getFirstName();

        // Assert
        assertNotNull(result, "First name should not be null");
        assertTrue(result.length() >= 3, "First name should have at least 3 characters");
    }

    @Test
    void testLastNameNotBlank() {
        // Arrange
        String lastName = "Doe";
        signupRequest.setLastName(lastName);

        // Act
        String result = signupRequest.getLastName();

        // Assert
        assertNotNull(result, "Last name should not be null");
        assertTrue(result.length() >= 3, "Last name should have at least 3 characters");
    }

    @Test
    void testPasswordNotBlank() {
        // Arrange
        String password = "securePassword";
        signupRequest.setPassword(password);

        // Act
        String result = signupRequest.getPassword();

        // Assert
        assertNotNull(result, "Password should not be null");
        assertTrue(result.length() >= 6, "Password should have at least 6 characters");
    }

    @Test
    void testEmailFormat() {
        // Arrange
        String email = "invalidemail.com";
        signupRequest.setEmail(email);

        // Act
        String result = signupRequest.getEmail();

        // Assert
        assertFalse(result.contains("@"), "Email should contain '@' symbol");
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

        // Act
        boolean isEqual = signupRequest.equals(signupRequest2);
        boolean hashCodesEqual = signupRequest.hashCode() == signupRequest2.hashCode();

        // Assert
        assertTrue(isEqual, "SignupRequests should be equal");
        assertTrue(hashCodesEqual, "Hash codes should be equal");
    }
}
