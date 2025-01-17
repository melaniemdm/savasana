package com.openclassrooms.starterjwt.services;
import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServicesTests {
    private SessionService sessionService;
    private SessionRepository sessionRepository;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        sessionRepository = mock(SessionRepository.class);
        userRepository = mock(UserRepository.class);
        sessionService = new SessionService(sessionRepository, userRepository);
    }

    @Test
    void testCreate() {
        // Arrange
        Session session = new Session();
        session.setId(1L);
        session.setName("Test Session");

        when(sessionRepository.save(session)).thenReturn(session);

        // Act
        Session result = sessionService.create(session);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Session", result.getName());
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    void testDelete() {
        // Arrange
        Long sessionId = 1L;

        // Act
        sessionService.delete(sessionId);

        // Assert
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }
    @Test
    void testFindAll() {
        // Arrange
        Session session1 = new Session();
        session1.setId(1L);

        Session session2 = new Session();
        session2.setId(2L);

        when(sessionRepository.findAll()).thenReturn(Arrays.asList(session1, session2));

        // Act
        var result = sessionService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(sessionRepository, times(1)).findAll();
    }
    @Test
    void testGetById() {
        // Arrange
        Long sessionId = 1L;
        Session session = new Session();
        session.setId(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act
        Session result = sessionService.getById(sessionId);

        // Assert
        assertNotNull(result);
        assertEquals(sessionId, result.getId());
        verify(sessionRepository, times(1)).findById(sessionId);
    }
    @Test
    void testUpdate() {
        // Arrange
        Long sessionId = 1L;
        Session session = new Session();
        session.setName("Updated Session");

        when(sessionRepository.save(session)).thenReturn(session);

        // Act
        Session result = sessionService.update(sessionId, session);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Session", result.getName());
        assertEquals(sessionId, result.getId());
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    void testParticipate_Success() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;

        // Création d'une session et d'un utilisateur simulés
        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(new ArrayList<>());

        User user = new User();
        user.setId(userId);

        // Simule les comportements des dépôts
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Act: Appel la méthode pour ajouter l'utilisateur à la session
        assertDoesNotThrow(() -> sessionService.participate(sessionId, userId));

        // Assert: Vérifier que l'utilisateur a été ajouté à la session
        assertEquals(1, session.getUsers().size(), "La session doit contenir un utilisateur");
        assertTrue(session.getUsers().contains(user), "La session doit contenir l'utilisateur ajouté");
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    void testParticipate_ThrowsNotFoundException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> sessionService.participate(sessionId, userId));
    }
    @Test
    void testNoLongerParticipate_Success() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;

        User user = new User();
        user.setId(userId);

        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(Collections.singletonList(user));

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenReturn(session);

        // Act
        assertDoesNotThrow(() -> sessionService.noLongerParticipate(sessionId, userId));

        // Assert
        verify(sessionRepository, times(1)).save(session);
    }
    @Test
    void testNoLongerParticipate_ThrowsBadRequestException() {
        // Arrange
        Long sessionId = 1L;
        Long userId = 2L;

        Session session = new Session();
        session.setId(sessionId);
        session.setUsers(Collections.emptyList());

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act & Assert
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(sessionId, userId));
    }


    }
