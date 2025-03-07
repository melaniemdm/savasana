package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTests {

    private User user;

    @BeforeEach
    void setUp() {
        // Création d'une instance de User avec des valeurs de test
        user = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("encodedPassword")
                .admin(false)
                .createdAt(LocalDateTime.now())  // Initialisation manuelle de createdAt
                .updatedAt(LocalDateTime.now())  // Initialisation manuelle de updatedAt
                .build();
    }

    @Test
    void testUserCreation() {
        // Arrange
        User expectedUser = user;

        // Act
        Long id = expectedUser.getId();
        String email = expectedUser.getEmail();
        String lastName = expectedUser.getLastName();
        String firstName = expectedUser.getFirstName();
        String password = expectedUser.getPassword();
        boolean isAdmin = expectedUser.isAdmin();
        LocalDateTime createdAt = expectedUser.getCreatedAt();
        LocalDateTime updatedAt = expectedUser.getUpdatedAt();

        // Assert
        assertNotNull(expectedUser);
        assertEquals(1L, id);
        assertEquals("testuser@example.com", email);
        assertEquals("Doe", lastName);
        assertEquals("John", firstName);
        assertEquals("encodedPassword", password);
        assertFalse(isAdmin);
        assertNotNull(createdAt);
        assertNotNull(updatedAt);
    }

    @Test
    void testUserEquality() {
        // Arrange
        User sameUser = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("encodedPassword")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User differentUser = User.builder()
                .id(2L)
                .email("anotheruser@example.com")
                .lastName("Smith")
                .firstName("Jane")
                .password("newPassword")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act et Assert
        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());
        assertNotEquals(user, differentUser);
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    void testUserFieldValidation() {
        // Arrange
        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String password = user.getPassword();

        // Act & Assert
        assertNotNull(email);
        assertTrue(email.length() <= 50);
        assertTrue(email.contains("@"));

        assertNotNull(firstName);
        assertTrue(firstName.length() <= 20);

        assertNotNull(lastName);
        assertTrue(lastName.length() <= 20);

        assertNotNull(password);
        assertTrue(password.length() <= 120);
    }

    @Test
    void testUserNullFields() {
        // Arrange, Act et Assert
        assertThrows(NullPointerException.class, () -> {
            User nullFieldUser = User.builder()
                    .email(null) // email est non-nul
                    .lastName("Doe")
                    .firstName("John")
                    .password("encodedPassword")
                    .admin(false)
                    .build();
        });

        assertThrows(NullPointerException.class, () -> {
            User nullFieldUser = User.builder()
                    .email("testuser@example.com")
                    .lastName(null) // lastName est non-nul
                    .firstName("John")
                    .password("encodedPassword")
                    .admin(false)
                    .build();
        });

        assertThrows(NullPointerException.class, () -> {
            User nullFieldUser = User.builder()
                    .email("testuser@example.com")
                    .lastName("Doe")
                    .firstName(null) // firstName est non-nul
                    .password("encodedPassword")
                    .admin(false)
                    .build();
        });

        assertThrows(NullPointerException.class, () -> {
            User nullFieldUser = User.builder()
                    .email("testuser@example.com")
                    .lastName("Doe")
                    .firstName("John")
                    .password(null) // password est non-nul
                    .admin(false)
                    .build();
        });
    }

    @Test
    void testUserCreatedAtUpdatedAt() {
        // Arrange
        LocalDateTime createdAt = user.getCreatedAt();
        LocalDateTime updatedAt = user.getUpdatedAt();

        // Act & Assert
        assertNotNull(createdAt);
        assertNotNull(updatedAt);
    }
    @Test
    void testUserToString() {
        // Arrange
        String result;

        // Act
        result = user.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("id=1"));
        assertTrue(result.contains("email=testuser@example.com"));
        assertTrue(result.contains("firstName=John"));
        assertTrue(result.contains("lastName=Doe"));
        assertTrue(result.contains("password=encodedPassword"));
        assertTrue(result.contains("admin=false"));
        assertTrue(result.contains("createdAt="));
        assertTrue(result.contains("updatedAt="));
    }
}
