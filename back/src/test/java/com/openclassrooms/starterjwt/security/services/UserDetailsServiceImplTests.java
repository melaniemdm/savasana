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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        // Création de l'utilisateur pour les tests
        user = new User("testuser@example.com", "Doe", "John", "encodedPassword", false);
        user.setId(1L);

        // Initialisation des mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange : Simuler le comportement de userRepository pour renvoyer un utilisateur
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

        // Act : Appel de la méthode
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername("testuser@example.com");

        // Assert : Vérification que l'utilisateur est bien retourné et que les propriétés sont correctes
        assertNotNull(userDetails);
        assertEquals("testuser@example.com", userDetails.getUsername());
        assertEquals("John", userDetails.getFirstName());
        assertEquals("Doe", userDetails.getLastName());
        assertEquals("encodedPassword", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange : Simuler le comportement de userRepository pour renvoyer un utilisateur inexistant
        when(userRepository.findByEmail("nonexistentuser@example.com")).thenReturn(Optional.empty());

        // Act & Assert : Vérification qu'une exception UsernameNotFoundException est lancée
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentuser@example.com"));
    }
}
