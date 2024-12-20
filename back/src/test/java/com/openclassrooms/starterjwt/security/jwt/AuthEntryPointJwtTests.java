package com.openclassrooms.starterjwt.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthEntryPointJwtTests {

    private AuthEntryPointJwt authEntryPointJwt;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private AuthenticationException mockAuthException;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() throws Exception {
        authEntryPointJwt = new AuthEntryPointJwt();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockAuthException = mock(AuthenticationException.class);

        // Simuler l'OutputStream
        outputStream = new ByteArrayOutputStream();
        DelegatingServletOutputStream servletOutputStream = new DelegatingServletOutputStream(outputStream);

        when(mockResponse.getOutputStream()).thenReturn(servletOutputStream);
        when(mockRequest.getServletPath()).thenReturn("/api/test");
    }

    @Test
    void testCommence_ShouldWriteUnauthorizedResponse() throws Exception {
        // Arrange
        when(mockAuthException.getMessage()).thenReturn("Unauthorized access");

        // Act
        authEntryPointJwt.commence(mockRequest, mockResponse, mockAuthException);

        // Capture des vérifications
        verify(mockResponse).setContentType("application/json");
        verify(mockResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Convertir la sortie JSON en map
        ObjectMapper mapper = new ObjectMapper();
        String jsonOutput = outputStream.toString();
        Map<String, Object> responseBody = mapper.readValue(jsonOutput, Map.class);

        // Assertions
        assertEquals(401, responseBody.get("status"));
        assertEquals("Unauthorized", responseBody.get("error"));
        assertEquals("Unauthorized access", responseBody.get("message"));
        assertEquals("/api/test", responseBody.get("path"));
    }
}
