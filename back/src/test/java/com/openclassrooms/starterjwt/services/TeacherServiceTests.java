package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTests {

    private TeacherService teacherService;
    private TeacherRepository teacherRepository;

    @BeforeEach
    void setUp() {
        // Mock du TeacherRepository
        teacherRepository = mock(TeacherRepository.class);
        // Initialisation du service avec le mock
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    void testFindAll_ReturnsTeachersList() {
        // Arrange
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("Teacher FirstName One");
        teacher1.setLastName("Teacher LastName One");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Teacher FirstName Two");
        teacher2.setLastName("Teacher LastName Two");
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        // Act
        List<Teacher> result = teacherService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Teacher FirstName One", result.get(0).getFirstName());
        assertEquals("Teacher LastName One", result.get(0).getLastName());
        assertEquals("Teacher FirstName Two", result.get(1).getFirstName());
        assertEquals("Teacher LastName Two", result.get(1).getLastName());

        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void testFindById_TeacherFound() {
        // Arrange
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setFirstName("Teacher FirstName One");
        teacher.setLastName("Teacher LastName One");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        // Act
        Teacher result = teacherService.findById(teacherId);

        // Assert
        assertNotNull(result);
        assertEquals(teacherId, result.getId());
        assertEquals("Teacher FirstName One", result.getFirstName());
        assertEquals("Teacher LastName One", result.getLastName());

        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    void testFindById_TeacherNotFound() {
        // Arrange
        Long teacherId = 1L;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        // Act
        Teacher result = teacherService.findById(teacherId);

        // Assert
        assertNull(result);

        verify(teacherRepository, times(1)).findById(teacherId);
    }
}
