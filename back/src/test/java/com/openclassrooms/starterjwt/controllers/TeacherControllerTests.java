package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;


class TeacherControllerTests {
    private TeacherService teacherService;
    private TeacherMapper teacherMapper;
    private TeacherController teacherController;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        teacherService = mock(TeacherService.class);
        teacherMapper = mock(TeacherMapper.class);

        // Initialisation explicite du contrôleur avec les mocks
        teacherController = new TeacherController(teacherService, teacherMapper);

        // Initialisation des objets
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John");
        teacherDto.setLastName("Doe");

        // Mocks pour les appels de service et de mapper
        when(teacherService.findById(1L)).thenReturn(teacher);
        when(teacherService.findAll()).thenReturn(Arrays.asList(teacher));
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);
        when(teacherMapper.toDto(Arrays.asList(teacher))).thenReturn(Arrays.asList(teacherDto));
    }

    @Test
    void testFindById_BadRequest() {
        // Act
        ResponseEntity<?> response = teacherController.findById("invalid");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }
    @Test
    void testFindById_Success() {
        // Act
        ResponseEntity<?> response = teacherController.findById("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(teacherDto, response.getBody());
    }
    @Test
    void testFindById_NotFound() {
        // Arrange
        when(teacherService.findById(1L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = teacherController.findById("1");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }
    @Test
    void testFindAll() {
        // Act
        ResponseEntity<?> response = teacherController.findAll();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(((List<?>) response.getBody()).contains(teacherDto));
    }
}
