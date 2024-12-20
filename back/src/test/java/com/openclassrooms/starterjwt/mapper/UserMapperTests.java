package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTests {

    private UserMapperImpl userMapper;

    @BeforeEach
    void setUp() {
        // Initialisation de la vraie instance de UserMapperImpl
        userMapper = new UserMapperImpl();
    }

    @Test
    void testToEntity() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("John");
        userDto.setLastName("Doe");
        userDto.setEmail("johndoe@example.com");  // Ajout de l'email nécessaire
        userDto.setPassword("password");
        userDto.setAdmin(true);
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        // Act
        User result = userMapper.toEntity(userDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("johndoe@example.com", result.getEmail());
        assertEquals("password", result.getPassword());
        assertTrue(result.isAdmin());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void testToDto() {
        // Arrange
        User user = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("janesmith@example.com") // Ajout de l'email nécessaire
                .password("encodedPassword")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Act
        UserDto result = userMapper.toDto(user);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        assertEquals("janesmith@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
        assertFalse(result.isAdmin());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void testToEntityList() {
        // Arrange
        UserDto dto1 = new UserDto();
        dto1.setId(1L);
        dto1.setFirstName("John");
        dto1.setLastName("Doe");
        dto1.setEmail("johndoe@example.com");  // Ajout de l'email nécessaire
        dto1.setPassword("password123");  // Ajout du mot de passe nécessaire

        UserDto dto2 = new UserDto();
        dto2.setId(2L);
        dto2.setFirstName("Jane");
        dto2.setLastName("Smith");
        dto2.setEmail("janesmith@example.com");  // Ajout de l'email nécessaire
        dto2.setPassword("password456");  // Ajout du mot de passe nécessaire

        // Act
        var resultList = userMapper.toEntity(Arrays.asList(dto1, dto2));

        // Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getId());
        assertEquals("John", resultList.get(0).getFirstName());
        assertEquals("johndoe@example.com", resultList.get(0).getEmail());  // Vérification de l'email
        assertEquals("password123", resultList.get(0).getPassword());  // Vérification du mot de passe
        assertEquals(2L, resultList.get(1).getId());
        assertEquals("Jane", resultList.get(1).getFirstName());
        assertEquals("janesmith@example.com", resultList.get(1).getEmail());  // Vérification de l'email
        assertEquals("password456", resultList.get(1).getPassword());  // Vérification du mot de passe
    }

    @Test
    void testToDtoList() {
        // Arrange
        UserDto dto1 = new UserDto();
        dto1.setId(1L);
        dto1.setFirstName("John");
        dto1.setLastName("Doe");
        dto1.setEmail("johndoe@example.com");
        dto1.setPassword("password123");  // Assurez-vous que le mot de passe est défini
        dto1.setAdmin(true);
        dto1.setCreatedAt(LocalDateTime.now());
        dto1.setUpdatedAt(LocalDateTime.now());

        UserDto dto2 = new UserDto();
        dto2.setId(2L);
        dto2.setFirstName("Jane");
        dto2.setLastName("Smith");
        dto2.setEmail("janesmith@example.com");
        dto2.setPassword("password456");  // Assurez-vous que le mot de passe est défini
        dto2.setAdmin(true);
        dto2.setCreatedAt(LocalDateTime.now());
        dto2.setUpdatedAt(LocalDateTime.now());

        // Act
        List<User> resultList = userMapper.toEntity(Arrays.asList(dto1, dto2));

        // Assert
        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(1L, resultList.get(0).getId());
        assertEquals("John", resultList.get(0).getFirstName());
        assertEquals("johndoe@example.com", resultList.get(0).getEmail());  // Vérification de l'email
        assertEquals("password123", resultList.get(0).getPassword());  // Vérification du mot de passe
        assertEquals(2L, resultList.get(1).getId());
        assertEquals("Jane", resultList.get(1).getFirstName());
        assertEquals("janesmith@example.com", resultList.get(1).getEmail());  // Vérification de l'email
        assertEquals("password456", resultList.get(1).getPassword());  // Vérification du mot de passe
    }
}
