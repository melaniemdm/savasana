package com.openclassrooms.starterjwt.integration.mapper;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapperImpl;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMapperIntegrationTests {

    private UserMapperImpl userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    public void testToEntity_NullDto() {
        User result = userMapper.toEntity((UserDto) null);
        assertNull(result, "toEntity should return null when the input DTO is null");
    }

    @Test
    public void testToEntity_ValidDto() {
        UserDto dto = new UserDto();
        dto.setId(1L);
        dto.setEmail("test@example.com");
        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setPassword("password123");
        dto.setAdmin(true);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUpdatedAt(LocalDateTime.now());

        User result = userMapper.toEntity(dto);

        assertNotNull(result, "toEntity should not return null for a valid DTO");
        assertEquals(dto.getId(), result.getId(), "ID should match");
        assertEquals(dto.getEmail(), result.getEmail(), "Email should match");
        assertEquals(dto.getFirstName(), result.getFirstName(), "First name should match");
        assertEquals(dto.getLastName(), result.getLastName(), "Last name should match");
        assertEquals(dto.getPassword(), result.getPassword(), "Password should match");
        assertEquals(dto.isAdmin(), result.isAdmin(), "Admin status should match");
        assertEquals(dto.getCreatedAt(), result.getCreatedAt(), "CreatedAt should match");
        assertEquals(dto.getUpdatedAt(), result.getUpdatedAt(), "UpdatedAt should match");
    }

    @Test
    public void testToDto_NullEntity() {
        UserDto result = userMapper.toDto((User) null);
        assertNull(result, "toDto should return null when the input entity is null");
    }

    @Test
    public void testToDto_ValidEntity() {
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        UserDto result = userMapper.toDto(user);

        assertNotNull(result, "toDto should not return null for a valid entity");
        assertEquals(user.getId(), result.getId(), "ID should match");
        assertEquals(user.getEmail(), result.getEmail(), "Email should match");
        assertEquals(user.getFirstName(), result.getFirstName(), "First name should match");
        assertEquals(user.getLastName(), result.getLastName(), "Last name should match");
        assertEquals(user.getPassword(), result.getPassword(), "Password should match");
        assertEquals(user.isAdmin(), result.isAdmin(), "Admin status should match");
        assertEquals(user.getCreatedAt(), result.getCreatedAt(), "CreatedAt should match");
        assertEquals(user.getUpdatedAt(), result.getUpdatedAt(), "UpdatedAt should match");
    }

    @Test
    public void testToEntity_NullList() {
        List<User> result = userMapper.toEntity((List<UserDto>) null);
        assertNull(result, "toEntity should return null when the input list is null");
    }


    @Test
    public void testToEntity_EmptyList() {
        List<User> result = userMapper.toEntity(new ArrayList<>());
        assertNotNull(result, "toEntity should not return null for an empty list");
        assertTrue(result.isEmpty(), "toEntity should return an empty list for an empty input");
    }



    @Test
    public void testToDto_EmptyList() {
        List<UserDto> result = userMapper.toDto(new ArrayList<>());
        assertNotNull(result, "toDto should not return null for an empty list");
        assertTrue(result.isEmpty(), "toDto should return an empty list for an empty input");
    }

    @Test
    public void testToDto_NullList() {
        List<UserDto> result = userMapper.toDto((List<User>) null);
        assertNull(result, "toDto should return null when the input list is null");
    }

    @Test
    public void testToEntity_ListWithValidDtos() {
        List<UserDto> dtoList = new ArrayList<>();

        // DTO 1 avec un password valide
        UserDto dto1 = new UserDto();
        dto1.setId(1L);
        dto1.setEmail("test1@example.com");
        dto1.setFirstName("John");
        dto1.setLastName("Doe");
        dto1.setPassword("password123"); // Ajout du mot de passe

        // DTO 2 avec un password valide
        UserDto dto2 = new UserDto();
        dto2.setId(2L);
        dto2.setEmail("test2@example.com");
        dto2.setFirstName("Jane");
        dto2.setLastName("Smith");
        dto2.setPassword("securePassword"); // Ajout du mot de passe

        dtoList.add(dto1);
        dtoList.add(dto2);

        // Conversion avec le mapper
        List<User> result = userMapper.toEntity(dtoList);

        // Assertions
        assertNotNull(result, "toEntity should not return null for a valid input list");
        assertEquals(2, result.size(), "toEntity should map the correct number of items");
        assertEquals(dto1.getEmail(), result.get(0).getEmail(), "Email of first item should match");
        assertEquals(dto2.getEmail(), result.get(1).getEmail(), "Email of second item should match");
        assertEquals(dto1.getPassword(), result.get(0).getPassword(), "Password of first item should match");
        assertEquals(dto2.getPassword(), result.get(1).getPassword(), "Password of second item should match");
    }
    @Test
    public void testToDto_ListWithValidEntities() {
        // Préparation de la liste d'entités User
        List<User> entityList = new ArrayList<>();

        User user1 = User.builder()
                .id(1L)
                .email("user1@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(2L)
                .email("user2@example.com")
                .firstName("Jane")
                .lastName("Smith")
                .password("securePassword")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entityList.add(user1);
        entityList.add(user2);

        // Appel de la méthode toDto pour convertir la liste
        List<UserDto> result = userMapper.toDto(entityList);

        // Assertions
        assertNotNull(result, "toDto should not return null for a valid input list");
        assertEquals(2, result.size(), "toDto should map the correct number of items");

        // Validation du mapping pour le premier élément
        UserDto dto1 = result.get(0);
        assertEquals(user1.getId(), dto1.getId(), "ID of the first item should match");
        assertEquals(user1.getEmail(), dto1.getEmail(), "Email of the first item should match");
        assertEquals(user1.getFirstName(), dto1.getFirstName(), "First name of the first item should match");
        assertEquals(user1.getLastName(), dto1.getLastName(), "Last name of the first item should match");
        assertEquals(user1.getPassword(), dto1.getPassword(), "Password of the first item should match");
        assertEquals(user1.isAdmin(), dto1.isAdmin(), "Admin status of the first item should match");

        // Validation du mapping pour le second élément
        UserDto dto2 = result.get(1);
        assertEquals(user2.getId(), dto2.getId(), "ID of the second item should match");
        assertEquals(user2.getEmail(), dto2.getEmail(), "Email of the second item should match");
        assertEquals(user2.getFirstName(), dto2.getFirstName(), "First name of the second item should match");
        assertEquals(user2.getLastName(), dto2.getLastName(), "Last name of the second item should match");
        assertEquals(user2.getPassword(), dto2.getPassword(), "Password of the second item should match");
        assertEquals(user2.isAdmin(), dto2.isAdmin(), "Admin status of the second item should match");
    }

}
