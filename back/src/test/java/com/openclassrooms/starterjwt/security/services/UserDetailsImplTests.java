package com.openclassrooms.starterjwt.security.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

class UserDetailsImplTests {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {

        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();
    }

    @Test
    void testGetAuthorities() {
        // Act
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void testIsAccountNonExpired() {
        // Act & Assert
        assertTrue(userDetails.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        // Act & Assert
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        // Act & Assert
        assertTrue(userDetails.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        // Act & Assert
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void testEquals_SameObject() {
        // Act
        boolean result = userDetails.equals(userDetails);

        // Assert
        assertTrue(result); // meme objet userDetails
    }

    @Test
    void testEquals_DifferentObjectSameId() {
        // Arrange
        UserDetailsImpl otherUser = UserDetailsImpl.builder()
                .id(1L)
                .username("testuser@example.com")
                .firstName("John")
                .lastName("Doe")
                .admin(false)
                .password("password")
                .build();

        // Act
        boolean result = userDetails.equals(otherUser);

        // Assert
        assertTrue(result);  // Les objets sont égaux car ils ont le même ID
    }

    @Test
    void testEquals_DifferentObjectDifferentId() {
        // Arrange
        UserDetailsImpl otherUser = UserDetailsImpl.builder()
                .id(2L)
                .username("otheruser@example.com")
                .firstName("Jane")
                .lastName("Doe")
                .admin(true)
                .password("password")
                .build();

        // Act
        boolean result = userDetails.equals(otherUser);

        // Assert
        assertFalse(result);  // Les objets ne sont pas égaux car les ID sont différents
    }
}
