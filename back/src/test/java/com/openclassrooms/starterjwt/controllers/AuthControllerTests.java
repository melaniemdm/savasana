package com.openclassrooms.starterjwt.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


class AuthControllerTests {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private Authentication authentication;
    private AuthController authController;
    private UserDetailsImpl userDetails;



    @BeforeEach
    void setUp() {
        // Mock des dépendances
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtils = mock(JwtUtils.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userRepository = mock(UserRepository.class);
        authentication = mock(Authentication.class);

        // Initialisation du contrôleur avec les mocks
        authController = new AuthController(authenticationManager, passwordEncoder, jwtUtils, userRepository);

        // Initialisation d'un UserDetailsImpl pour les tests
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("encodedPassword")
                .build();
    }
    @Test
    void testAuthenticateUser_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("testuser@example.com");
        loginRequest.setPassword("password");

        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("mockJwtToken");
        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertEquals("mockJwtToken", jwtResponse.getToken());
        assertEquals("testuser@example.com", jwtResponse.getUsername());
        assertEquals("John", jwtResponse.getFirstName());
        assertEquals("Doe", jwtResponse.getLastName());

    }

    @Test
    void testRegisterUser_Success() {
        // Arrange
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");

        when(userRepository.existsByEmail("newuser@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertNotNull(messageResponse);
        assertEquals("User registered successfully!", messageResponse.getMessage());
    }

    @Test
    void testRegisterUser_EmailAlreadyTaken() {
        // Arrange
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setEmail("test@example.com");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act
        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        // Assert
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        MessageResponse messageResponse = (MessageResponse) response.getBody();
        assertNotNull(messageResponse);
        assertEquals("Error: Email is already taken!", messageResponse.getMessage());
}
}
