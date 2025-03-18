package com.openclassrooms.starterjwt.integration.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")

public class TeacherServiceIntegrationTests {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        // Création et sauvegarde d'un enseignant
        teacher = new Teacher();
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        teacher = teacherRepository.save(teacher);
    }
    @Test
    void testFindAll() {
        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertNotNull(teachers, "La liste des enseignants ne doit pas être null");
        assertFalse(teachers.isEmpty(), "La liste des enseignants ne doit pas être vide");
        boolean containsTeacher = teachers.stream().anyMatch(t -> t.getId().equals(teacher.getId()));
        assertTrue(containsTeacher, "La liste doit contenir l'enseignant créé dans le setUp()");
    }
    @Test
    void testFindById() {
        // Act
        Teacher foundTeacher = teacherService.findById(teacher.getId());

        // Assert
        assertNotNull(foundTeacher, "L'enseignant doit être trouvé par son ID");
        assertEquals("John", foundTeacher.getFirstName(), "Le nom de l'enseignant doit correspondre à celui défini");
    }
    @Test
    void testFindByIdNotFound() {
        // Act
        Teacher notFoundTeacher = teacherService.findById(9999L);

        // Assert
        assertNull(notFoundTeacher, "La recherche d'un enseignant inexistant doit retourner null");
    }
    @Test
    void testFindAllAfterAddingMultipleTeachers() {
        // Ajout d'un deuxième enseignant
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Doe");
        teacher2 = teacherRepository.save(teacher2);

        // Ajout d'un troisième enseignant
        Teacher teacher3 = new Teacher();
        teacher3.setFirstName("Alice");
        teacher3.setLastName("Smith");
        teacher3 = teacherRepository.save(teacher3);

        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertTrue(teachers.size() >= 3, "La liste doit contenir au moins 3 enseignants");
        assertTrue(teachers.stream().anyMatch(t -> t.getId().equals(teacher.getId())), "La liste doit contenir l'enseignant initial");
        Teacher finalTeacher = teacher2;
        assertTrue(teachers.stream().anyMatch(t -> t.getId().equals(finalTeacher.getId())), "La liste doit contenir le deuxième enseignant");
        Teacher finalTeacher1 = teacher3;
        assertTrue(teachers.stream().anyMatch(t -> t.getId().equals(finalTeacher1.getId())), "La liste doit contenir le troisième enseignant");
    }
    @Test
    void testFindAllEmpty() {
        // Supprime tous les enseignants
        teacherRepository.deleteAll();

        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertNotNull(teachers, "La liste ne doit pas être null");
        assertTrue(teachers.isEmpty(), "La liste des enseignants doit être vide après suppression de tous les enregistrements");
    }
    @Test
    void testMultipleTeacherCreation() {
        // Création de deux enseignants supplémentaires
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Jane");
        teacher2.setLastName("Smith");
        teacher2 = teacherRepository.save(teacher2);

        Teacher teacher3 = new Teacher();
        teacher3.setFirstName("Alice");
        teacher3.setLastName("Johnson");
        teacher3 = teacherRepository.save(teacher3);

        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertTrue(teachers.size() >= 3, "La liste doit contenir au moins 3 enseignants");
        assertTrue(teachers.stream().anyMatch(t -> t.getId().equals(teacher.getId())), "La liste doit contenir l'enseignant initial");
        assertTrue(teachers.stream().anyMatch(t -> t.getFirstName().equals("Jane")), "La liste doit contenir l'enseignant 'Jane'");
        assertTrue(teachers.stream().anyMatch(t -> t.getFirstName().equals("Alice")), "La liste doit contenir l'enseignant 'Alice'");
    }
    @Test
    void testFindByIdWithNegativeId() {
        // Act
        Teacher negativeTeacher = teacherService.findById(-1L);

        // Assert
        assertNull(negativeTeacher, "La recherche d'un enseignant avec un ID négatif doit retourner null");
    }
    @Test
    void testFindByIdMultipleTeachers() {
        // Ajout d'un deuxième enseignant
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Emily");
        teacher2.setLastName("Bronte");
        teacher2 = teacherRepository.save(teacher2);

        // Act
        Teacher foundTeacher1 = teacherService.findById(teacher.getId());
        Teacher foundTeacher2 = teacherService.findById(teacher2.getId());

        // Assert
        assertNotNull(foundTeacher1, "L'enseignant initial doit être trouvé");
        assertNotNull(foundTeacher2, "Le deuxième enseignant doit être trouvé");
        assertEquals("John", foundTeacher1.getFirstName(), "Le prénom de l'enseignant initial doit être 'John'");
        assertEquals("Emily", foundTeacher2.getFirstName(), "Le prénom du deuxième enseignant doit être 'Emily'");
    }
    @Test
    void testFindAllAfterDeletingOneTeacher() {
        // Ajout d'un deuxième enseignant
        Teacher teacher2 = new Teacher();
        teacher2.setFirstName("Paul");
        teacher2.setLastName("McCartney");
        teacher2 = teacherRepository.save(teacher2);

        // Vérifie la taille initiale
        List<Teacher> teachersBeforeDeletion = teacherService.findAll();
        int sizeBefore = teachersBeforeDeletion.size();

        // Supprime l'enseignant initial
        teacherRepository.deleteById(teacher.getId());

        // Act
        List<Teacher> teachersAfterDeletion = teacherService.findAll();

        // Assert
        assertEquals(sizeBefore - 1, teachersAfterDeletion.size(), "Le nombre d'enseignants doit diminuer après suppression");
        assertFalse(teachersAfterDeletion.stream().anyMatch(t -> t.getId().equals(teacher.getId())), "L'enseignant supprimé ne doit plus être présent");
    }
}
