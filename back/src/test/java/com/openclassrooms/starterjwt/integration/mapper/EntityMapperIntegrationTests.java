package com.openclassrooms.starterjwt.integration.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;

import com.openclassrooms.starterjwt.models.Session;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class EntityMapperIntegrationTests {
    @Autowired
    private SessionMapper sessionMapper; // Le bean généré par MapStruct est injecté ici

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Configuration des mocks
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setFirstName("Teacher 1");
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setFirstName("Teacher 2");
        when(teacherService.findById(1L)).thenReturn(teacher1);
        when(teacherService.findById(2L)).thenReturn(teacher2);

        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("User 1");
        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("User 2");
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);
    }

    @Test
    public void testToEntity_NullList() {
        // Arrange
        List<SessionDto> inputList = null;

        // Act
        List<Session> result = sessionMapper.toEntity(inputList);

        // Assert
        assertNull(result, "toEntity should return null when the input list is null");
    }
    @Test
    public void testToEntity_EmptyList() {
        List<SessionDto> inputList = new ArrayList<>();
        List<Session> result = sessionMapper.toEntity(inputList);
        assertNotNull(result, "toEntity should not return null when input is an empty list");
        assertTrue(result.isEmpty(), "toEntity should return an empty list when input is empty");
    }
    @Test
    public void testToEntity_NullSessionDto() {
        Session result = sessionMapper.toEntity((SessionDto) null);
        assertNull(result, "toEntity should return null when input SessionDto is null");
    }
    @Test
    public void testToDto_NullList() {
        List<Session> inputList = null;
        List<SessionDto> result = sessionMapper.toDto(inputList);
        assertNull(result, "toDto should return null when the input list is null");
    }
    @Test
    public void testToDto_NullSession() {
        SessionDto dto = sessionMapper.toDto((Session) null);
        assertNull(dto, "toDto should return null when input Session is null");
    }
    @Test
    public void testToDto_ListContainingNull() {
        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Session 1");

        List<Session> entityList = Arrays.asList(session1, null);
        List<SessionDto> dtoList = sessionMapper.toDto(entityList);

        assertNotNull(dtoList, "Result should not be null");
        assertEquals(2, dtoList.size(), "List should have 2 elements");
        assertNotNull(dtoList.get(0), "First element should be mapped");
        assertNull(dtoList.get(1), "Second element should be null");
    }
    @Test
    public void testRoundTrip_SingleSession() {
        SessionDto originalDto = new SessionDto();
        originalDto.setId(5L);
        originalDto.setName("RoundTrip Session");
        originalDto.setTeacher_id(1L);
        originalDto.setUsers(Arrays.asList(1L));

        Session session = sessionMapper.toEntity(originalDto);
        assertNotNull(session, "Mapped Session should not be null");

        SessionDto roundTripDto = sessionMapper.toDto(session);
        assertNotNull(roundTripDto, "Round-trip SessionDto should not be null");
        assertEquals(originalDto.getId(), roundTripDto.getId(), "Round-trip ID should match");
        assertEquals(originalDto.getName(), roundTripDto.getName(), "Round-trip name should match");
    }
    @Test
    public void testToDto_ValidList() {
        List<Session> entityList = new ArrayList<>();
        Session session1 = new Session();
        session1.setId(1L);
        session1.setName("Session 1");

        Session session2 = new Session();
        session2.setId(2L);
        session2.setName("Session 2");

        entityList.add(session1);
        entityList.add(session2);

        List<SessionDto> dtoList = sessionMapper.toDto(entityList);

        assertNotNull(dtoList, "toDto should not return null for a valid input list");
        assertEquals(2, dtoList.size(), "toDto should map the correct number of items");
        assertEquals(session1.getId(), dtoList.get(0).getId(), "ID should match for the first item");
        assertEquals(session1.getName(), dtoList.get(0).getName(), "Name should match for the first item");
    }
    @Test
    public void testToEntity_ListContainingNull() {
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("Session 1");
        sessionDto1.setTeacher_id(1L);
        sessionDto1.setUsers(Arrays.asList(1L));

        List<SessionDto> dtoList = Arrays.asList(sessionDto1, null);
        List<Session> entityList = sessionMapper.toEntity(dtoList);

        assertNotNull(entityList, "Resulting list should not be null");
        assertEquals(2, entityList.size(), "List should have 2 elements");
        assertNotNull(entityList.get(0), "First element should be mapped");
        assertNull(entityList.get(1), "Second element should be null");
    }
    @Test
    public void testToEntity_ValidList() {
        List<SessionDto> dtoList = new ArrayList<>();

        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("Session 1");
        // Fournir les champs obligatoires
        sessionDto1.setTeacher_id(1L);
        sessionDto1.setUsers(Arrays.asList(1L));

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Session 2");
        sessionDto2.setTeacher_id(2L);
        sessionDto2.setUsers(Arrays.asList(2L));

        dtoList.add(sessionDto1);
        dtoList.add(sessionDto2);

        List<Session> entityList = sessionMapper.toEntity(dtoList);

        assertNotNull(entityList, "toEntity should not return null for a valid input list");
        assertEquals(2, entityList.size(), "toEntity should map the correct number of items");
        assertEquals(sessionDto1.getId(), entityList.get(0).getId(), "ID should match for the first item");
        assertEquals(sessionDto1.getName(), entityList.get(0).getName(), "Name should match for the first item");
    }
}
