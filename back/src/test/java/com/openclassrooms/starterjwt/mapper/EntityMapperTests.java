package com.openclassrooms.starterjwt.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

// Test pour EntityMapper
public class EntityMapperTests {

    private EntityMapper<TestDto, TestEntity> entityMapper;

    // DTO et Entity pour le test
    static class TestDto {
        public Long id;
        public String name;

        public TestDto(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    static class TestEntity {
        public Long id;
        public String name;

        public TestEntity(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    // Fake Implementation pour le test
    private static class TestEntityMapper implements EntityMapper<TestDto, TestEntity> {

        @Override
        public TestEntity toEntity(TestDto dto) {
            return new TestEntity(dto.id, dto.name);
        }

        @Override
        public TestDto toDto(TestEntity entity) {
            return new TestDto(entity.id, entity.name);
        }

        @Override
        public List<TestEntity> toEntity(List<TestDto> dtoList) {
            return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
        }

        @Override
        public List<TestDto> toDto(List<TestEntity> entityList) {
            return entityList.stream().map(this::toDto).collect(Collectors.toList());
        }
    }

    @BeforeEach
    void setUp() {
        // Initialisation de la fausse implémentation
        entityMapper = new TestEntityMapper();
    }

    @Test
    void testToEntity() {
        // Arrange
        TestDto dto = new TestDto(1L, "Test DTO");

        // Act
        TestEntity entity = entityMapper.toEntity(dto);

        // Assert
        assertNotNull(entity);
        assertEquals(1L, entity.id);
        assertEquals("Test DTO", entity.name);
    }

    @Test
    void testToDto() {
        // Arrange
        TestEntity entity = new TestEntity(2L, "Test Entity");

        // Act
        TestDto dto = entityMapper.toDto(entity);

        // Assert
        assertNotNull(dto);
        assertEquals(2L, dto.id);
        assertEquals("Test Entity", dto.name);
    }

    @Test
    void testToEntityList() {
        // Arrange
        List<TestDto> dtoList = Arrays.asList(
                new TestDto(1L, "DTO 1"),
                new TestDto(2L, "DTO 2")
        );

        // Act
        List<TestEntity> entityList = entityMapper.toEntity(dtoList);

        // Assert
        assertNotNull(entityList);
        assertEquals(2, entityList.size());
        assertEquals("DTO 1", entityList.get(0).name);
        assertEquals("DTO 2", entityList.get(1).name);
    }

    @Test
    void testToDtoList() {
        // Arrange
        List<TestEntity> entityList = Arrays.asList(
                new TestEntity(1L, "Entity 1"),
                new TestEntity(2L, "Entity 2")
        );

        // Act
        List<TestDto> dtoList = entityMapper.toDto(entityList);

        // Assert
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size());
        assertEquals("Entity 1", dtoList.get(0).name);
        assertEquals("Entity 2", dtoList.get(1).name);
    }
}
