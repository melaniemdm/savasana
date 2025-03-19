package com.openclassrooms.starterjwt.integration.controller;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import org.springframework.test.context.ActiveProfiles;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class SessionControllerIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    public void setup() {
        // Activation de la sécurité
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // sets up Spring Security with MockMvc
                .build();
        // Nettoyage de la base de données pour chaque test
        sessionRepository.deleteAll();

        Session session = new Session();
        session.setName("Test Session");
        session.setDescription("Description for Test Session");
        session.setDate(new Date());
        sessionRepository.save(session);
    }

    @Test
    public void testFindById_Success() throws Exception {
        // Récupération d'un ID valide depuis la base de données
        Session testSession = sessionRepository.findAll().get(0);

        // Act et Assert
        mockMvc.perform(get("/api/session/" + testSession.getId())
                        .with(user("admin").password("pass").roles("USER","ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Session"))
                .andExpect(jsonPath("$.description").value("Description for Test Session"));
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        // Act et Assert
        mockMvc.perform(get("/api/session/999")
                        .with(user("admin").password("pass").roles("USER","ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindById_BadRequest() throws Exception {
        // Act et Assert
        mockMvc.perform(get("/api/session/invalid-id")
                        .with(user("admin").password("pass").roles("USER","ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testFindAll_Success() throws Exception {
        // Arrange
        Session session1 = new Session();
        session1.setName("Session 1");
        session1.setDescription("Description for Session 1");
        session1.setDate(new Date());

        Session session2 = new Session();
        session2.setName("Session 2");
        session2.setDescription("Description for Session 2");
        session2.setDate(new Date());

        sessionRepository.save(session1);
        sessionRepository.save(session2);

        // Act et Assert
        mockMvc.perform(get("/api/session")
                        .with(user("admin").password("pass").roles("USER","ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Session"))
                .andExpect(jsonPath("$[0].description").value("Description for Test Session"))
                .andExpect(jsonPath("$[1].name").value("Session 1"))
                .andExpect(jsonPath("$[1].description").value("Description for Session 1"))
                .andExpect(jsonPath("$[2].name").value("Session 2"))
                .andExpect(jsonPath("$[2].description").value("Description for Session 2"));
    }
    @Test
    public void testCreate_Success() throws Exception {

        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("New Session");
        sessionDto.setDescription("Description for New Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L); // Ajout d'un ID de professeur valide

        // S'assure que l'enseignant existe dans la base de données
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("firstNameTeacher");


        // Act et Assert
        mockMvc.perform(post("/api/session/")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Session"))
                .andExpect(jsonPath("$.description").value("Description for New Session"));
    }
    @Test
    public void testCreate_BadRequest() throws Exception {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Description without a name");
        sessionDto.setDate(new Date());

        // Act et Assert
        mockMvc.perform(post("/api/session")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testUpdate_Success() throws Exception {
        // Arrange
        // Ajoute un Teacher valide dans la bd
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");

        // Ajoute une Session existante
        Session existingSession = sessionRepository.findAll().get(0);

        // Prépare un SessionDto avec un teacher_id valide
        SessionDto updatedSessionDto = new SessionDto();
        updatedSessionDto.setName("Updated Session");
        updatedSessionDto.setDescription("Updated description for session");
        updatedSessionDto.setDate(new Date());
        updatedSessionDto.setTeacher_id(teacher.getId());

        // Act & Assert
        mockMvc.perform(put("/api/session/" + existingSession.getId())
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedSessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Session"))
                .andExpect(jsonPath("$.description").value("Updated description for session"));

        // Vérification dans la bd
        Session updatedSession = sessionRepository.findById(existingSession.getId()).orElseThrow();
        assertEquals("Updated Session", updatedSession.getName());
        assertEquals("Updated description for session", updatedSession.getDescription());
        assertEquals(teacher.getId(), updatedSession.getTeacher().getId());
    }
    @Test
    public void testUpdate_BadRequest_InvalidId() throws Exception {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Invalid Update");
        sessionDto.setDescription("Invalid update description");
        sessionDto.setDate(new Date());

        // Act & Assert
        mockMvc.perform(put("/api/session/invalid-id")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testDelete_Success() throws Exception {
        // Arrange
        Session session = sessionRepository.findAll().get(0);

        // Act & Assert
        mockMvc.perform(delete("/api/session/" + session.getId())
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Vérification dans la bd
        assertFalse(sessionRepository.findById(session.getId()).isPresent());
    }
    @Test
    public void testDelete_BadRequest_InvalidId() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/session/invalid-id")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testDelete_NotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/session/99999")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
public void testParticipate_Success() throws Exception {
    // Arrange
    Session session = new Session();
    session.setName("Session for Participation");
    session.setDescription("Description for Participation");
    session.setDate(new Date());
    session = sessionRepository.save(session);

    User user = new User();
    user.setId(1L);
    user.setFirstName("John");
    user.setLastName("Doe");
    user.setEmail("john.doe@example.com");
    user.setPassword("password");

    // Act & Assert
    mockMvc.perform(post("/api/session/" + session.getId() + "/participate/" + user.getId())
                    .with(user("admin").password("pass").roles("USER", "ADMIN"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

    // Vérification que l'utilisateur est ajouté à la session
    Session updatedSession = sessionRepository.findById(session.getId()).orElse(null);
    assertNotNull(updatedSession);
    assertEquals(1, updatedSession.getUsers().size());
    assertEquals(user.getId(), updatedSession.getUsers().get(0).getId());
}

    @Test
    public void testParticipate_BadRequest_InvalidId() throws Exception {
        // Act & Assert
        mockMvc.perform(post("/api/session/invalid-id/participate/1")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNoLongerParticipate_BadRequest_InvalidId() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/session/invalid-id/participate/1")
                        .with(user("admin").password("pass").roles("USER", "ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
