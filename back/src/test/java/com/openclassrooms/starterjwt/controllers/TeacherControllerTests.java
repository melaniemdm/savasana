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

import java.util.Arrays;
import java.util.List;

class TeacherControllerTests {


    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Initialisation des objets
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John Doe");
        teacher.setLastName("John Doe");

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("John Doe");
        teacherDto.setLastName("John Doe");
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
