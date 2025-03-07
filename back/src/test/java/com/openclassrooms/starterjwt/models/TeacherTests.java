package com.openclassrooms.starterjwt.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeacherTests {

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Crée une instance de Teacher avec des valeurs de test
        teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())  // Initialisation manuelle de createdAt
                .updatedAt(LocalDateTime.now())  // Initialisation manuelle de updatedAt
                .build();
    }

    @Test
    void testTeacherCreation() {
        // Arrange
        Teacher expectedTeacher = teacher;

        // Act & Assert
        assertNotNull(expectedTeacher);
        assertEquals(1L, expectedTeacher.getId());
        assertEquals("John", expectedTeacher.getFirstName());
        assertEquals("Doe", expectedTeacher.getLastName());
        assertNotNull(expectedTeacher.getCreatedAt());
        assertNotNull(expectedTeacher.getUpdatedAt());
    }

    @Test
    void testTeacherEquality() {
        // Arrange
        Teacher sameTeacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();



        // Teste l'égalité avec un enseignant différent
        Teacher differentTeacher = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act & Assert
        assertEquals(teacher, sameTeacher);
        assertEquals(teacher.hashCode(), sameTeacher.hashCode());

        assertNotEquals(teacher, differentTeacher);
        assertNotEquals(teacher.hashCode(), differentTeacher.hashCode());
    }

    @Test
    void testTeacherFieldValidation() {
        // Arrange
        String firstName = teacher.getFirstName();
        String lastName = teacher.getLastName();

        // Act & Assert
        assertNotNull(teacher.getFirstName());
        assertTrue(teacher.getFirstName().length() <= 20);

        assertNotNull(teacher.getLastName());
        assertTrue(teacher.getLastName().length() <= 20);
    }
    @Test
    void testToString() {
        // Arrange
        String result;

        // Act
        result = teacher.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("Teacher(id=1"));
        assertTrue(result.contains("firstName=John"));
        assertTrue(result.contains("lastName=Doe"));
        assertTrue(result.contains("createdAt="));
        assertTrue(result.contains("updatedAt="));
    }
}
