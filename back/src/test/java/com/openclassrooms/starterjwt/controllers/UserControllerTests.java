package com.openclassrooms.starterjwt.controllers;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;

import java.util.Optional;

class UserControllerTests {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Authentication authentication;  // Correctement mocké Authentication

    @InjectMocks
    private UserController userController;

    private User user;
    private UserDto userDto;
    private UserDetails userDetails;  // Correctement défini pour les tests

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);

        // Initialisation des objets
        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setFirstName("John Doe");
        user.setLastName("John Doe");

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("john.doe@example.com");
        userDto.setFirstName("John Doe");
        userDto.setLastName("John Doe");

        // Simuler le comportement de userService et userMapper
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(userService.findById(999L)).thenReturn(null);

        // Simuler un objet UserDetails pour l'utilisateur authentifié
        userDetails = mock(UserDetails.class);  // Création d'un mock UserDetails
        when(userDetails.getUsername()).thenReturn("john.doe@example.com");  // Simuler le username
        when(authentication.getPrincipal()).thenReturn(userDetails);  // Lier authentication au mock UserDetails
        SecurityContextHolder.getContext().setAuthentication(authentication);  // Configuration du SecurityContext
    }

    @Test
    void testFindById_Success() {
        // Act
        ResponseEntity<?> response = userController.findById("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void testFindById_NotFound() {
        // Act
        ResponseEntity<?> response = userController.findById("999");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testSave_Success() {
        // Act
        ResponseEntity<?> response = userController.save("1");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(userService, times(1)).delete(1L);  // Vérifie que la méthode delete a été appelée une fois
    }

    @Test
    void testSave_Unauthorized() {
        // Simuler un utilisateur authentifié différent
        when(userDetails.getUsername()).thenReturn("other.email@example.com");

        // Act
        ResponseEntity<?> response = userController.save("1");

        // Assert
        assertEquals(401, response.getStatusCodeValue());
    }

    @Test
    void testSave_BadRequest() {
        // Act
        ResponseEntity<?> response = userController.save("invalid");

        // Assert
        assertEquals(400, response.getStatusCodeValue());
    }
}
