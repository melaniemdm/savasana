package com.openclassrooms.starterjwt.integration.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapperImpl;
import com.openclassrooms.starterjwt.models.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
public class EntityMapperIntegrationTests {
    private SessionMapperImpl sessionMapper;

    @BeforeEach
    public void setUp() {
        sessionMapper = new SessionMapperImpl();
    }

    @Test
    public void testToEntity_NullList() {
        List<Session> result = sessionMapper.toEntity((List<SessionDto>) null);
        assertNull(result, "toEntity should return null when input is null");
    }

    @Test
    public void testToEntity_EmptyList() {
        List<Session> result = sessionMapper.toEntity(new ArrayList<>());
        assertNotNull(result, "toEntity should not return null when input is an empty list");
        assertTrue(result.isEmpty(), "toEntity should return an empty list when input is empty");
    }

    @Test
    public void testToEntity_ValidList() {
        List<SessionDto> dtoList = new ArrayList<>();
        SessionDto sessionDto1 = new SessionDto();
        sessionDto1.setId(1L);
        sessionDto1.setName("Session 1");

        SessionDto sessionDto2 = new SessionDto();
        sessionDto2.setId(2L);
        sessionDto2.setName("Session 2");

        dtoList.add(sessionDto1);
        dtoList.add(sessionDto2);

        List<Session> entityList = sessionMapper.toEntity(dtoList);

        assertNotNull(entityList, "toEntity should not return null for a valid input list");
        assertEquals(2, entityList.size(), "toEntity should map the correct number of items");
        assertEquals(sessionDto1.getId(), entityList.get(0).getId(), "ID should match for the first item");
        assertEquals(sessionDto1.getName(), entityList.get(0).getName(), "Name should match for the first item");
    }


    @Test
    public void testToDto_EmptyList() {
        List<SessionDto> result = sessionMapper.toDto(new ArrayList<>());
        assertNotNull(result, "toDto should not return null when input is an empty list");
        assertTrue(result.isEmpty(), "toDto should return an empty list when input is empty");
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
}
