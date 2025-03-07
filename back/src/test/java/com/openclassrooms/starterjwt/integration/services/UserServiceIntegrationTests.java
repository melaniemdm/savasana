package com.openclassrooms.starterjwt.integration.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
public class UserServiceIntegrationTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        // Création et sauvegarde d'un utilisateur
        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@test.com");
        user = userRepository.save(user);
    }
    @Test
    void testFindById() {
        // Act
        User foundUser = userService.findById(user.getId());

        // Assert
        assertNotNull(foundUser, "L'utilisateur doit être trouvé par son ID");
        assertEquals(user.getEmail(), foundUser.getEmail(), "L'email de l'utilisateur doit correspondre à celui défini");

    }
    @Test
    void testDelete() {
        // Act
        userService.delete(user.getId());

        // Assert
        User foundUser = userService.findById(user.getId());
        assertNull(foundUser, "L'utilisateur ne doit pas être trouvé après suppression");
    }
    @Test
    void testFindByIdNotFound() {
        // Act
        User foundUser = userService.findById(99999L);
        // Assert
        assertNull(foundUser, "La recherche d'un utilisateur inexistant doit retourner null");
    }
    @Test
    void testDeleteNonExistentUser() {
        // Act & Assert : La suppression d'un utilisateur inexistant doit lancer une EmptyResultDataAccessException
        assertThrows(org.springframework.dao.EmptyResultDataAccessException.class,
                () -> userService.delete(99999L),
                "La suppression d'un utilisateur inexistant doit lancer EmptyResultDataAccessException");
    }
    @Test
    void testMultipleUserCreationAndDeletion() {
        // Création de deux utilisateurs supplémentaires
        User user2 = new User();
        user2.setFirstName("Alice");
        user2.setLastName("Wonderland");
        user2.setEmail("alice@test.com");
        user2 = userRepository.save(user2);

        User user3 = new User();
        user3.setFirstName("Bob");
        user3.setLastName("Builder");
        user3.setEmail("bob@test.com");
        user3 = userRepository.save(user3);

        // Vérification que tous les utilisateurs existent
        assertNotNull(userService.findById(user.getId()), "Utilisateur 1 doit exister");
        assertNotNull(userService.findById(user2.getId()), "Utilisateur 2 doit exister");
        assertNotNull(userService.findById(user3.getId()), "Utilisateur 3 doit exister");

        // Suppression de user2 et user3
        userService.delete(user2.getId());
        userService.delete(user3.getId());

        // Vérification de la suppression
        assertNull(userService.findById(user2.getId()), "Utilisateur 2 doit être supprimé");
        assertNull(userService.findById(user3.getId()), "Utilisateur 3 doit être supprimé");

        // Vérification que l'utilisateur initial existe toujours
        assertNotNull(userService.findById(user.getId()), "Utilisateur 1 doit toujours exister");
    }
    @Test
    void testUpdateUserEmail() {
        // Mise à jour de l'email directement via le repository
        String newEmail = "mel.new@test.com";
        user.setEmail(newEmail);
        user = userRepository.save(user);

        // Act
        User updatedUser = userService.findById(user.getId());

        // Assert
        assertNotNull(updatedUser, "L'utilisateur mis à jour doit être trouvé");
        assertEquals(newEmail, updatedUser.getEmail(), "L'email de l'utilisateur doit être mis à jour");
    }
    @Test
    void testUpdateUserName() {
        // Mise à jour du prénom et du nom
        String newFirstName = "melanie";
        String newLastName = "lauraline";
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user = userRepository.save(user);

        // Act
        User updatedUser = userService.findById(user.getId());

        // Assert
        assertNotNull(updatedUser, "L'utilisateur mis à jour doit être trouvé");
        assertEquals(newFirstName, updatedUser.getFirstName(), "Le prénom doit être mis à jour");
        assertEquals(newLastName, updatedUser.getLastName(), "Le nom doit être mis à jour");
    }
}
