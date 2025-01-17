package com.openclassrooms.starterjwt.security.services;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;

class UserDetailsServiceImplTests {
    private UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsService;
    private User user;

    @BeforeEach
    void setUp() {
        // Initialisation des mocks
        userRepository = mock(UserRepository.class);

        // Initialisation du service avec le mock
        userDetailsService = new UserDetailsServiceImpl(userRepository);

        // Création de l'utilisateur pour les tests
        user = new User("testuser@example.com", "Doe", "John", "encodedPassword", false);
        user.setId(1L);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        // Act
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("testuser@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser@example.com", userDetails.getUsername());
        assertEquals("John", userDetails.getFirstName());
        assertEquals("Doe", userDetails.getLastName());
        assertEquals("encodedPassword", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByEmail("nonexistentuser@example.com")).thenReturn(Optional.empty());

        // Act et Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("nonexistentuser@example.com"));
    }
}
