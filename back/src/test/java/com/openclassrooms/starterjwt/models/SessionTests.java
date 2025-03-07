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
        // Arrange & Act
      // Assert
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
        // Arrange
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

        // Act & Assert
        assertEquals(session, sameSession);
        assertEquals(session.hashCode(), sameSession.hashCode());


    }

    @Test
    void testSessionNoteEquals(){
        // Arrange
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

        // Act & Assert
        assertNotEquals(session, differentSession);
        assertNotEquals(session.hashCode(), differentSession.hashCode());

    }

    @Test
    void testSessionValidations() {
        // Arrange & Act
        String name = session.getName();
        String description = session.getDescription();
        Date date = session.getDate();

        // Assert
        assertNotNull(name);
        assertTrue(name.length() <= 50);

        assertNotNull(description);
        assertTrue(description.length() <= 2500);

        assertNotNull(date);
    }

    @Test
    void testSessionWithTeacherAndUsers() {
        // Arrange
        Teacher sessionTeacher = session.getTeacher();
        var sessionUsers = session.getUsers();

        // Act & Assert
        assertNotNull(sessionTeacher);
        assertEquals("John", sessionTeacher.getFirstName());
        assertEquals("Doe", sessionTeacher.getLastName());

        assertNotNull(sessionUsers);
        assertEquals(1, sessionUsers.size());
        assertEquals("testuser@example.com", sessionUsers.get(0).getEmail());
    }

    @Test
    void testSessionTimestamps() {
        // Arrange
        LocalDateTime createdAt = session.getCreatedAt();
        LocalDateTime updatedAt = session.getUpdatedAt();

        // Act & Assert
        assertNotNull(createdAt);
        assertNotNull(updatedAt);
    }
}
