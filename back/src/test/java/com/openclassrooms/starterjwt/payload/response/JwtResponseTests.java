package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtResponseTests {

    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet JwtResponse avec des valeurs fictives
        jwtResponse = new JwtResponse(
                "testToken",
                1L,
                "testuser@example.com",
                "John",
                "Doe",
                true
        );
    }

    @Test
    void testJwtResponseConstructor() {
        // Arrange
        String expectedToken = "testToken";
        Long expectedId = 1L;
        String expectedUsername = "testuser@example.com";
        String expectedFirstName = "John";
        String expectedLastName = "Doe";
        boolean expectedAdmin = true;

        // Act
        JwtResponse result = jwtResponse;

        // Assert
        assertNotNull(result);
        assertEquals(expectedToken, result.getToken());
        assertEquals(expectedId, result.getId());
        assertEquals(expectedUsername, result.getUsername());
        assertEquals(expectedFirstName, result.getFirstName());
        assertEquals(expectedLastName, result.getLastName());
        assertTrue(result.getAdmin());
    }

    @Test
    void testGetToken() {
        // Arrange
        String expectedToken = "testToken";

        // Act
        String result = jwtResponse.getToken();

        // Assert
        assertEquals(expectedToken, result);
    }

    @Test
    void testGetType() {
        // Arrange
        String expectedType = "Bearer";

        // Act
        String result = jwtResponse.getType();

        // Assert
        assertEquals(expectedType, result);
    }

    @Test
    void testGetId() {
        // Arrange
        Long expectedId = 1L;

        // Act
        Long result = jwtResponse.getId();

        // Assert
        assertEquals(expectedId, result);
    }

    @Test
    void testGetUsername() {
        // Arrange
        String expectedUsername = "testuser@example.com";

        // Act
        String result = jwtResponse.getUsername();

        // Assert
        assertEquals(expectedUsername, result);
    }

    @Test
    void testGetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        String result = jwtResponse.getFirstName();

        // Assert
        assertEquals(expectedFirstName, result);
    }

    @Test
    void testGetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        String result = jwtResponse.getLastName();

        // Assert
        assertEquals(expectedLastName, result);
    }

    @Test
    void testGetAdmin() {
        // Arrange
        boolean expectedAdmin = true;

        // Act
        boolean result = jwtResponse.getAdmin();

        // Assert
        assertTrue(result);
    }
}
