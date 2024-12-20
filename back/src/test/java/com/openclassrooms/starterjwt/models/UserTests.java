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
        // Tester que l'instance a été correctement créée
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testuser@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("encodedPassword", user.getPassword());
        assertFalse(user.isAdmin());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void testUserEquality() {
        // Tester l'égalité de deux utilisateurs avec le même ID
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

        assertEquals(user, sameUser);
        assertEquals(user.hashCode(), sameUser.hashCode());

        // Tester l'égalité avec un utilisateur différent
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

        assertNotEquals(user, differentUser);
        assertNotEquals(user.hashCode(), differentUser.hashCode());
    }

    @Test
    void testUserFieldValidation() {
        // Tester les contraintes @Size et @Email
        assertNotNull(user.getEmail());
        assertTrue(user.getEmail().length() <= 50);
        assertTrue(user.getEmail().contains("@"));

        assertNotNull(user.getFirstName());
        assertTrue(user.getFirstName().length() <= 20);

        assertNotNull(user.getLastName());
        assertTrue(user.getLastName().length() <= 20);

        assertNotNull(user.getPassword());
        assertTrue(user.getPassword().length() <= 120);
    }

    @Test
    void testUserNullFields() {
        // Tester un utilisateur avec des champs obligatoires nuls
        assertThrows(NullPointerException.class, () -> {
            User nullFieldUser = User.builder()
                    .email(null) // email est non-nul, donc une exception devrait être levée ici
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
        // Tester les timestamps `createdAt` et `updatedAt`
        LocalDateTime createdAt = user.getCreatedAt();
        LocalDateTime updatedAt = user.getUpdatedAt();

        assertNotNull(createdAt);
        assertNotNull(updatedAt);
    }
    @Test
    void testUserToString() {
        // Tester la méthode toString()
        String result = user.toString();

        // Vérifier que les éléments attendus sont présents dans la chaîne
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
