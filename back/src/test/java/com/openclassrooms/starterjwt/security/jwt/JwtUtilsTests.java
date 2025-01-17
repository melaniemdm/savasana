package com.openclassrooms.starterjwt.security.jwt;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class JwtUtilsTests {

    private JwtUtils jwtUtils;
    private Authentication authentication;
    private UserDetailsImpl userDetails;

    private String jwtSecret = "testSecretKey";
    private int jwtExpirationMs = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        authentication = mock(Authentication.class);

        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("encodedPassword")
                .build();

        // Initialiser l'instance de JwtUtils si elle est null
        if (jwtUtils == null) {
            jwtUtils = new JwtUtils();
        }

        try {
            setPrivateField(jwtUtils, "jwtSecret", jwtSecret);
            setPrivateField(jwtUtils, "jwtExpirationMs", jwtExpirationMs);
        } catch (Exception e) {
            fail("Failed to set private fields: " + e.getMessage());
        }
    }
    private void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }


    @Test
    void testGetUserNameFromJwtToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        // Act
        String username = jwtUtils.getUserNameFromJwtToken(token);

        // Assert
        assertEquals("testuser@example.com", username);
    }

    @Test
    void testValidateJwtToken_ValidToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        // Act
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateJwtToken_ExpiredToken() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser@example.com")
                .setIssuedAt(new Date(System.currentTimeMillis() - jwtExpirationMs - 1000))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        // Act
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_InvalidSignature() {
        // Arrange
        String token = Jwts.builder()
                .setSubject("testuser@example.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, "wrongSecret")
                .compact();

        // Act
        boolean isValid = jwtUtils.validateJwtToken(token);

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_MalformedToken() {
        // Act
        boolean isValid = jwtUtils.validateJwtToken("malformedToken");

        // Assert
        assertFalse(isValid);
    }

    @Test
    void testValidateJwtToken_EmptyClaims() {
        // Act
        boolean isValid = jwtUtils.validateJwtToken("");

        // Assert
        assertFalse(isValid);
    }
}
