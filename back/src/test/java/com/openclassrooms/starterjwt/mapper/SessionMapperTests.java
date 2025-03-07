package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class SessionMapperTests {
    private SessionMapper sessionMapper; // Mapper à tester
    private TeacherService teacherServiceMock;
    private UserService userServiceMock;
    @BeforeEach
    void setUp() {
        // Mocks des services
        teacherServiceMock = mock(TeacherService.class);
        userServiceMock = mock(UserService.class);

        // Création de l'implémentation et injection des mocks
        sessionMapper = Mappers.getMapper(SessionMapper.class);
        ((SessionMapperImpl) sessionMapper).teacherService = teacherServiceMock;
        ((SessionMapperImpl) sessionMapper).userService = userServiceMock;
    }

    @Test
    void testToEntity(){
        // Arrange
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Test Session");
        sessionDto.setTeacher_id(1L);
        sessionDto.setUsers(Arrays.asList(2L, 3L));

        // Mocke les retours des services avec les bons types
        Teacher mockTeacher = new Teacher();
        mockTeacher.setId(1L);

        User mockUser1 = new User();
        mockUser1.setId(2L);
        User mockUser2 = new User();
        mockUser2.setId(3L);

        when(teacherServiceMock.findById(1L)).thenReturn(mockTeacher);
        when(userServiceMock.findById(2L)).thenReturn(mockUser1);
        when(userServiceMock.findById(3L)).thenReturn(mockUser2);

        // Act
        Session session = sessionMapper.toEntity(sessionDto);

        // Assert
        assertNotNull(session);
        assertEquals("Test Session", session.getDescription());
        assertNotNull(session.getTeacher());
        assertEquals(1L, session.getTeacher().getId());
        assertEquals(2, session.getUsers().size());
        verify(teacherServiceMock, times(1)).findById(1L);
        verify(userServiceMock, times(2)).findById(anyLong());
    }
    @Test
    void testToDto() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(2L);

        User user2 = new User();
        user2.setId(3L);

        // Création de l'entité Session
        Session session = new Session();
        session.setDescription("Test Session");
        session.setTeacher(teacher);
        session.setUsers(Arrays.asList(user1, user2));

        // Act
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Assert
        assertNotNull(sessionDto);
        assertEquals("Test Session", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        List<Long> userIds = sessionDto.getUsers();
        assertNotNull(userIds);
        assertEquals(2, userIds.size());
        assertTrue(userIds.containsAll(Arrays.asList(2L, 3L))); // Vérifie que les IDs des utilisateurs sont corrects
    }
}
