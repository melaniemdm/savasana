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
        // Tester que l'instance a été correctement créée
        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("John", teacher.getFirstName());
        assertEquals("Doe", teacher.getLastName());
        assertNotNull(teacher.getCreatedAt());
        assertNotNull(teacher.getUpdatedAt());
    }

    @Test
    void testTeacherEquality() {
        // Tester l'égalité de deux enseignants avec le même ID
        Teacher sameTeacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertEquals(teacher, sameTeacher);
        assertEquals(teacher.hashCode(), sameTeacher.hashCode());

        // Tester l'égalité avec un enseignant différent
        Teacher differentTeacher = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        assertNotEquals(teacher, differentTeacher);
        assertNotEquals(teacher.hashCode(), differentTeacher.hashCode());
    }

    @Test
    void testTeacherFieldValidation() {
        // Tester les contraintes @NotBlank et @Size
        assertNotNull(teacher.getFirstName());
        assertTrue(teacher.getFirstName().length() <= 20);

        assertNotNull(teacher.getLastName());
        assertTrue(teacher.getLastName().length() <= 20);
    }
    @Test
    void testToString() {
        // Tester que la méthode toString() génère correctement la chaîne de caractères
        String result = teacher.toString();

        // Affichage pour vérifier la sortie exacte de toString()
        System.out.println("Teacher toString: " + result);

        // Vérifier que la chaîne contient les éléments essentiels
        //assertTrue(result.contains("Teacher(id=1, firstName=John, lastName=Doe)"));

        // Vérifier que la chaîne contient les dates de manière approximative (les dates changent à chaque exécution)
        assertTrue(result.contains("createdAt="));
        assertTrue(result.contains("updatedAt="));
    }
}
