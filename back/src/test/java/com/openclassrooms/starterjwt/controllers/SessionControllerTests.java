package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

class SessionControllerTests {

    @Mock
    private SessionService sessionService;

    @Mock
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks avec Mockito
        MockitoAnnotations.openMocks(this);  // Cela initialise les mocks

        // Création d'un exemple de session pour les tests
        session = new Session();
        session.setId(1L);
        session.setName("Test Session");
        session.setDate(new Date());
        session.setDescription("Test Description");

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Test Session");
        sessionDto.setDate(new Date());
        sessionDto.setDescription("Test Description");

        // Mock du comportement du service et du mapper
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionService.findAll()).thenReturn(Arrays.asList(session));
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);
    }

    @Test
    void testFindById_Success() {
        // Act
        ResponseEntity<?> response = sessionController.findById("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(sessionService.getById(999L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = sessionController.findById("999");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testFindById_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.findById("invalid");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }



    @Test
    void testCreate() {
        // Arrange
        when(sessionService.create(any(Session.class))).thenReturn(session);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);

        // Act
        ResponseEntity<?> response = sessionController.create(sessionDto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sessionDto, response.getBody());
    }



    @Test
    void testUpdate_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.update("invalid", sessionDto);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        // Act
        ResponseEntity<?> response = sessionController.save("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).delete(1L);
    }

    @Test
    void testDelete_NotFound() {
        // Arrange
        when(sessionService.getById(999L)).thenReturn(null);

        // Act
        ResponseEntity<?> response = sessionController.save("999");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.save("invalid");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testParticipate_Success() {
        // Act
        ResponseEntity<?> response = sessionController.participate("1", "1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).participate(1L, 1L);
    }

    @Test
    void testParticipate_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.participate("invalid", "1");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testNoLongerParticipate_Success() {
        // Act
        ResponseEntity<?> response = sessionController.noLongerParticipate("1", "1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(sessionService, times(1)).noLongerParticipate(1L, 1L);
    }

    @Test
    void testNoLongerParticipate_BadRequest() {
        // Act
        ResponseEntity<?> response = sessionController.noLongerParticipate("invalid", "1");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }
}
