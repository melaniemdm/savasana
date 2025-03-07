package com.openclassrooms.starterjwt.integration.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
public class SessionMapperIntegrationTests {
    @Autowired
    private SessionMapper sessionMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        // Création et sauvegarde d'un teacher
        teacher = new Teacher();
        teacher.setFirstName("Teacher");
        teacher.setLastName("Test");
        teacher = teacherRepository.save(teacher);

        // Création et sauvegarde d'un user
        user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@user.com");
        user = userRepository.save(user);
    }
    @Test
    void testToEntity() {
        // Création d'un SessionDto de test
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Session de test");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(1L));

        // Mappe en entité
        Session session = sessionMapper.toEntity(sessionDto);

        // Vérifie que la description est correctement mappée
        assertEquals("Session de test", session.getDescription());

        // Vérifie que le teacher est correctement mappé via teacherService
        assertNotNull(session.getTeacher(), "Le teacher ne doit pas être null");
        assertEquals(1L, session.getTeacher().getId());

        // Vérifie que la liste des users est correctement mappée via userService
        List<User> mappedUsers = session.getUsers();
        assertNotNull(mappedUsers, "La liste des utilisateurs ne doit pas être null");
        assertFalse(mappedUsers.isEmpty(), "La liste des utilisateurs ne doit pas être vide");
        assertEquals(1L, mappedUsers.get(0).getId());
    }

    @Test
    void testToDto() {
        // Création d'une Session de test avec des objets Teacher et User
        Session session = new Session();
        session.setDescription("Session inverse");

        Teacher teacherForSession = new Teacher();
        teacherForSession.setId(2L);
        teacherForSession.setLastName("Teacher 2");
        session.setTeacher(teacherForSession);

        User userForSession = new User();
        userForSession.setId(3L);
        userForSession.setFirstName("Alice");
        userForSession.setLastName("Smith");
        session.setUsers(Collections.singletonList(userForSession));

        // Mappe en DTO
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Vérifie que la description est correctement mappée
        assertEquals("Session inverse", sessionDto.getDescription());

        // Vérifie que l'ID du teacher est correctement mappé
        assertEquals(2L, sessionDto.getTeacher_id());

        // Vérifie que la liste des users est correctement mappée
        List<Long> userIds = sessionDto.getUsers();
        assertNotNull(userIds, "La liste des IDs d'utilisateurs ne doit pas être null");
        assertFalse(userIds.isEmpty(), "La liste des IDs d'utilisateurs ne doit pas être vide");
        assertEquals(3L, userIds.get(0));
    }
    @Test
    void testToEntity_NullTeacherId() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Session sans teacher");
        sessionDto.setTeacher_id(null);
        sessionDto.setUsers(Arrays.asList(user.getId()));

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertNotNull(session);
        assertEquals("Session sans teacher", session.getDescription());
        assertNull(session.getTeacher(), "Le teacher doit être null si teacher_id est null");
        assertNotNull(session.getUsers());
        assertEquals(1, session.getUsers().size());
    }
    @Test
    void testToEntity_NullUsersList() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Session sans users");
        sessionDto.setTeacher_id(teacher.getId());
        sessionDto.setUsers(null);

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertNotNull(session);
        assertEquals("Session sans users", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(teacher.getId(), session.getTeacher().getId());
        assertNotNull(session.getUsers(), "La liste des users ne doit pas être null");
        assertTrue(session.getUsers().isEmpty(), "La liste des users doit être vide si le DTO a null pour users");
    }
    @Test
    void testToEntity_EmptyUsersList() {
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Session avec liste vide");
        sessionDto.setTeacher_id(teacher.getId());
        sessionDto.setUsers(Collections.emptyList());

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertNotNull(session);
        assertEquals("Session avec liste vide", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(teacher.getId(), session.getTeacher().getId());
        assertNotNull(session.getUsers());
        assertTrue(session.getUsers().isEmpty(), "La liste des users doit être vide si le DTO a une liste vide");
    }
    @Test
    void testToDto_NullTeacher() {
        // Arrange
        Session session = new Session();
        session.setDescription("Session sans teacher en entité");
        session.setTeacher(null);
        session.setUsers(Collections.singletonList(user));

        // Act
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert
        assertNotNull(sessionDto);
        assertEquals("Session sans teacher en entité", sessionDto.getDescription());
        assertNull(sessionDto.getTeacher_id(), "teacher_id doit être null si aucun teacher n'est défini");
        List<Long> userIds = sessionDto.getUsers();
        assertNotNull(userIds);
        assertFalse(userIds.isEmpty());
        assertEquals(user.getId(), userIds.get(0));
    }
    @Test
    void testToDto_NullSession() {
        // Act
        SessionDto sessionDto = sessionMapper.toDto((Session) null);

        // Assert
        assertNull(sessionDto, "La conversion d'une session null doit retourner null");
    }
    @Test
    void testToDto_NullUsersList() {
        // Arrange
        Session session = new Session();
        session.setDescription("Session avec users null");
        Teacher teacherForSession = new Teacher();
        teacherForSession.setId(teacher.getId());
        teacherForSession.setFirstName("Teacher");
        teacherForSession.setLastName("Test");
        session.setTeacher(teacherForSession);
        session.setUsers(null);

        // Act
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert
        assertNotNull(sessionDto);
        assertEquals("Session avec users null", sessionDto.getDescription());
        assertEquals(teacher.getId(), sessionDto.getTeacher_id());
        assertNotNull(sessionDto.getUsers(), "La liste des IDs d'utilisateurs ne doit pas être null");
        assertTrue(sessionDto.getUsers().isEmpty(), "La liste des IDs d'utilisateurs doit être vide si la session a null pour users");
    }
}
