package com.openclassrooms.starterjwt.integration.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Test d'intégration pour la classe SessionService.
 * - Lance un vrai contexte Spring
 * - Utilise une base de données embarquée (H2) via @AutoConfigureTestDatabase
 * - Vérifie la persistance des données et l'interaction avec les repositories
 */
@SpringBootTest
@ActiveProfiles("test")

public class SessionServiceIntegrationTests {
    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Session session;
    private List<User> users = new ArrayList<>();

    @BeforeEach
    void setUp() {
        // Création d'un user
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@test.com");
        user = userRepository.save(user);

        // Création d'une session
        session = new Session();
        session.setName("Spring Boot Workshop");
        session.setDescription("Description initiale de la session");
        session.setDate(new Date());
        session.setUsers(new ArrayList<>());
        session = sessionRepository.save(session);
    }
    @Test
    void testCreateSession() {
        // Arrange
        Session newSession = new Session();
        newSession.setName("New Session");
        // Initialisation des champs obligatoires
        newSession.setDescription("Description de la nouvelle session");
        newSession.setDate(new Date());

        // Act
        Session created = sessionService.create(newSession);

        // Assert
        assertNotNull(created.getId(), "L'ID de la session créée ne doit pas être null");
        assertEquals("New Session", created.getName(), "Le nom de la session doit être celui spécifié");

    }
    @Test
    void testFindAll() {
        // Act
        var sessions = sessionService.findAll();

        // Assert

        assertFalse(sessions.isEmpty(), "La liste de sessions ne doit pas être vide");
        assertTrue(sessions.stream().anyMatch(s -> s.getName().equals("Spring Boot Workshop")),
                "La session créée dans setUp() doit être présente dans la liste");
    }
    @Test
    void testGetById() {
        // Act
        Session found = sessionService.getById(session.getId());

        // Assert
        assertNotNull(found, "La session trouvée ne doit pas être null");
        assertEquals(session.getName(), found.getName(),
                "Le nom de la session trouvée doit correspondre à celle créée initialement");
    }
    @Test
    void testUpdateSession() {
        // Arrange
        String updatedName = "Updated Session";
        session.setName(updatedName);

        // Act
        sessionService.update(session.getId(), session);
        Session updated = sessionService.getById(session.getId());

        // Assert
        assertEquals(updatedName, updated.getName(), "Le nom de la session doit avoir été modifié");
    }
    @Test
    void testDeleteSession() {
        // Act
        sessionService.delete(session.getId());
        Session deleted = sessionService.getById(session.getId());

        // Assert
        assertNull(deleted, "Après suppression, getById devrait retourner null");
    }
    @Test
    void testParticipate() {
        // Act
        sessionService.participate(session.getId(), user.getId());
        Session updated = sessionService.getById(session.getId());

        // Assert
        assertEquals(1, updated.getUsers().size(), "La session doit contenir 1 participant après l'ajout");
        assertEquals(user.getId(), updated.getUsers().get(0).getId(), "Le participant doit être le user créé dans setUp()");
    }
    @Test
    void testNoLongerParticipate() {
        // Arrange
        sessionService.participate(session.getId(), user.getId());
        Session updated = sessionService.getById(session.getId());
        assertEquals(1, updated.getUsers().size(), "On vérifie que l'utilisateur participe bien");

        // Act
        sessionService.noLongerParticipate(session.getId(), user.getId());
        updated = sessionService.getById(session.getId());

        // Assert
        assertTrue(updated.getUsers().isEmpty(), "Après retrait, la session ne doit plus contenir l'utilisateur");
    }
}
