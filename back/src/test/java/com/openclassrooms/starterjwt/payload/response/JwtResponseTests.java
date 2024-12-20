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
        // Vérification que le constructeur initialise correctement l'objet
        assertNotNull(jwtResponse);
        assertEquals("testToken", jwtResponse.getToken());
        assertEquals(1L, jwtResponse.getId());
        assertEquals("testuser@example.com", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());
        assertTrue(jwtResponse.getAdmin());
    }

    @Test
    void testGetToken() {
        // Vérification du getter pour 'token'
        assertEquals("testToken", jwtResponse.getToken());
    }

    @Test
    void testGetType() {
        // Vérification que le type est bien "Bearer" par défaut
        assertEquals("Bearer", jwtResponse.getType());
    }

    @Test
    void testGetId() {
        // Vérification du getter pour 'id'
        assertEquals(1L, jwtResponse.getId());
    }

    @Test
    void testGetUsername() {
        // Vérification du getter pour 'username'
        assertEquals("testuser@example.com", jwtResponse.getUsername());
    }

    @Test
    void testGetFirstName() {
        // Vérification du getter pour 'firstName'
        assertEquals("John", jwtResponse.getFirstName());
    }

    @Test
    void testGetLastName() {
        // Vérification du getter pour 'lastName'
        assertEquals("Doe", jwtResponse.getLastName());
    }

    @Test
    void testGetAdmin() {
        // Vérification du getter pour 'admin'
        assertTrue(jwtResponse.getAdmin());
    }
}
