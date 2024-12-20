package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageResponceTests {

    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet MessageResponse avec un message fictif
        messageResponse = new MessageResponse("Success");
    }

    @Test
    void testMessageResponseConstructor() {
        // Vérification que le constructeur initialise correctement l'objet
        assertNotNull(messageResponse);
        assertEquals("Success", messageResponse.getMessage());
    }

    @Test
    void testGetMessage() {
        // Vérification du getter pour 'message'
        assertEquals("Success", messageResponse.getMessage());
    }

    @Test
    void testSetMessage() {
        // Vérification que la méthode setMessage fonctionne correctement
        messageResponse.setMessage("Failure");
        assertEquals("Failure", messageResponse.getMessage());
    }
}
