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
    private TestDto testDto;
    private TestEntity testEntity;
    private List<TestDto> testDtoList;
    private List<TestEntity> testEntityList;

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

        // Initialisation des données pour les tests
        testDto = new TestDto(1L, "Test DTO");
        testEntity = new TestEntity(2L, "Test Entity");

        testDtoList = Arrays.asList(
                new TestDto(1L, "DTO 1"),
                new TestDto(2L, "DTO 2")
        );

        testEntityList = Arrays.asList(
                new TestEntity(1L, "Entity 1"),
                new TestEntity(2L, "Entity 2")
        );
    }

    @Test
    void testToEntity() {
        // Act
        TestEntity result = entityMapper.toEntity(testDto);

        // Assert
        assertNotNull(result, "L'entité ne doit pas être null");
        assertEquals(1L, result.id, "L'id de l'entité doit être correct");
        assertEquals("Test DTO", result.name, "Le nom de l'entité doit être correct");
    }

    @Test
    void testToDto() {
        // Act
        TestDto result = entityMapper.toDto(testEntity);

        // Assert
        assertNotNull(result, "Le DTO ne doit pas être null");
        assertEquals(2L, result.id, "L'id du DTO doit être correct");
        assertEquals("Test Entity", result.name, "Le nom du DTO doit être correct");
    }

    @Test
    void testToEntityList() {
        // Act
        List<TestEntity> result = entityMapper.toEntity(testDtoList);

        // Assert
        assertNotNull(result, "La liste d'entités ne doit pas être null");
        assertEquals(2, result.size(), "La liste d'entités doit contenir 2 éléments");
        assertEquals("DTO 1", result.get(0).name, "Le nom du premier élément doit être correct");
        assertEquals("DTO 2", result.get(1).name, "Le nom du second élément doit être correct");
    }

    @Test
    void testToDtoList() {
        // Act
        List<TestDto> result = entityMapper.toDto(testEntityList);

        // Assert
        assertNotNull(result, "La liste de DTO ne doit pas être null");
        assertEquals(2, result.size(), "La liste de DTO doit contenir 2 éléments");
        assertEquals("Entity 1", result.get(0).name, "Le nom du premier élément doit être correct");
        assertEquals("Entity 2", result.get(1).name, "Le nom du second élément doit être correct");
    }
}
