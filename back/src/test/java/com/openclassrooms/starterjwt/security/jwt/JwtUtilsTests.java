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

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    private UserDetailsImpl userDetails;

    private String jwtSecret = "testSecretKey";
    private int jwtExpirationMs = 3600000; // 1 hour

    @BeforeEach
    void setUp() {
        // Initialisation d'un UserDetailsImpl pour les tests
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
            jwtUtils = new JwtUtils();  // Assurez-vous que jwtUtils est bien instancié
        }

        // Utilisation de réflexion pour accéder et modifier les variables privées
        try {
            Field jwtSecretField = JwtUtils.class.getDeclaredField("jwtSecret");
            jwtSecretField.setAccessible(true); // Permet l'accès aux champs privés
            jwtSecretField.set(jwtUtils, jwtSecret); // Définir la valeur de jwtSecret

            Field jwtExpirationMsField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
            jwtExpirationMsField.setAccessible(true);
            jwtExpirationMsField.set(jwtUtils, jwtExpirationMs); // Définir la valeur de jwtExpirationMs
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            fail("Failed to access private fields");
        }
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
