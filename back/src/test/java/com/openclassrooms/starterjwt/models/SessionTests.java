package com.openclassrooms.starterjwt.models;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class SessionTests {
    private Session session;
    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        // Création d'une instance de Teacher pour l'association
        teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Création d'une instance de User pour l'association
        user = User.builder()
                .id(1L)
                .email("testuser@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("encodedPassword")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Création de l'instance de Session
        session = Session.builder()
                .id(1L)
                .name("Test Session")
                .date(new Date())
                .description("This is a test session.")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testSessionCreation() {
        // Tester que l'instance a été correctement créée
        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Test Session", session.getName());
        assertEquals("This is a test session.", session.getDescription());
        assertNotNull(session.getDate());
        assertNotNull(session.getCreatedAt());
        assertNotNull(session.getUpdatedAt());
    }

    @Test
    void testSessionEquality() {
        // Tester l'égalité de deux sessions avec le même ID
        Session sameSession = Session.builder()
                .id(1L)
                .name("Test Session")
                .date(new Date())
                .description("This is a test session.")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(session, sameSession);
        assertEquals(session.hashCode(), sameSession.hashCode());

        // Tester l'égalité avec une session différente
        Session differentSession = Session.builder()
                .id(2L)
                .name("Different Session")
                .date(new Date())
                .description("This is a different session.")
                .teacher(teacher)
                .users(Arrays.asList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertNotEquals(session, differentSession);
        assertNotEquals(session.hashCode(), differentSession.hashCode());
    }

    @Test
    void testSessionValidations() {
        // Tester que les validations de taille et de non-nullité fonctionnent
        assertNotNull(session.getName());
        assertTrue(session.getName().length() <= 50);

        assertNotNull(session.getDescription());
        assertTrue(session.getDescription().length() <= 2500);

        assertNotNull(session.getDate());
    }

    @Test
    void testSessionWithTeacherAndUsers() {
        // Tester l'association avec Teacher et User
        assertNotNull(session.getTeacher());
        assertEquals("John", session.getTeacher().getFirstName());
        assertEquals("Doe", session.getTeacher().getLastName());

        assertNotNull(session.getUsers());
        assertEquals(1, session.getUsers().size());
        assertEquals("testuser@example.com", session.getUsers().get(0).getEmail());
    }

    @Test
    void testSessionTimestamps() {
        // Tester que les timestamps `createdAt` et `updatedAt` sont bien initialisés
        LocalDateTime createdAt = session.getCreatedAt();
        LocalDateTime updatedAt = session.getUpdatedAt();

        assertNotNull(createdAt);
        assertNotNull(updatedAt);
    }
}
