package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherMappersTests {

    private TeacherMapperImpl teacherMapper;

    @BeforeEach
    void setUp() {
        // Initialisation d'une vraie instance du TeacherMapperImpl
        teacherMapper = new TeacherMapperImpl();
    }

    @Test
    void testToEntity() {
        // Arrange
        TeacherDto dto = new TeacherDto();
        dto.setId(1L);
        dto.setFirstName("Teacher FirstName");
        dto.setLastName("Teacher LastName");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        // Act
        Teacher result = teacherMapper.toEntity(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Teacher FirstName", result.getFirstName());
        assertEquals("Teacher LastName", result.getLastName());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void testToDto() {
        // Arrange
        Teacher entity = Teacher.builder()
                .id(2L)
                .firstName("Teacher FirstName Entity")
                .lastName("Teacher LastName Entity")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        TeacherDto result = teacherMapper.toDto(entity);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Teacher FirstName Entity", result.getFirstName());
        assertEquals("Teacher LastName Entity", result.getLastName());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void testToEntityList() {
        // Arrange
        TeacherDto dto1 = new TeacherDto();
        dto1.setId(1L);
        dto1.setFirstName("Teacher 1");
        dto1.setLastName("LastName 1");

        TeacherDto dto2 = new TeacherDto();
        dto2.setId(2L);
        dto2.setFirstName("Teacher 2");
        dto2.setLastName("LastName 2");

        // Act
        var resultList = teacherMapper.toEntity(Arrays.asList(dto1, dto2));

        // Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getId());
        assertEquals("Teacher 1", resultList.get(0).getFirstName());
        assertEquals(2L, resultList.get(1).getId());
        assertEquals("Teacher 2", resultList.get(1).getFirstName());
    }

    @Test
    void testToDtoList() {
        // Arrange
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .firstName("Teacher 1")
                .lastName("LastName 1")
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(2L)
                .firstName("Teacher 2")
                .lastName("LastName 2")
                .build();

        // Act
        var resultList = teacherMapper.toDto(Arrays.asList(teacher1, teacher2));

        // Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getId());
        assertEquals("Teacher 1", resultList.get(0).getFirstName());
        assertEquals(2L, resultList.get(1).getId());
        assertEquals("Teacher 2", resultList.get(1).getFirstName());
    }
}
