package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageResponseTests {

    private MessageResponse messageResponse;

    @BeforeEach
    void setUp() {
        // Initialisation de l'objet MessageResponse avec un message fictif
        messageResponse = new MessageResponse("Success");
    }

    @Test
    void testMessageResponseConstructor() {
        // Arrange
        String expectedMessage = "Success";

        // Act
        MessageResponse result = messageResponse;

        // Assert
        assertNotNull(result, "MessageResponse should not be null");
        assertEquals(expectedMessage, result.getMessage(), "Message should match the expected value");
    }

    @Test
    void testGetMessage() {
        // Arrange
        String expectedMessage = "Success";

        // Act
        String result = messageResponse.getMessage();

        // Assert
        assertEquals(expectedMessage, result, "getMessage should return the correct message");

    }

    @Test
    void testSetMessage() {
        // Arrange
        String newMessage = "Failure";

        // Act
        messageResponse.setMessage(newMessage);
        String result = messageResponse.getMessage();

        // Assert
        assertEquals(newMessage, result, "setMessage should update the message");
    }
}
