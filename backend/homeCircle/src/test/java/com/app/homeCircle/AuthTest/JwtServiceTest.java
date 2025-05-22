package com.app.homeCircle.AuthTest;

import com.app.homeCircle.Auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(JwtService.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserDetails userDetails;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_NOMBRE = "Test User";

    @BeforeEach
    void setup() {
        when(userDetails.getUsername()).thenReturn(TEST_EMAIL);
    }

    @Test
    void testGenerateToken() {
        // Act
        String token = jwtService.getToken(TEST_EMAIL, TEST_NOMBRE);

        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testGetEmailFromToken() {
        // Arrange
        String token = jwtService.getToken(TEST_EMAIL, TEST_NOMBRE);

        // Act
        String extractedEmail = jwtService.getEmailFromToken(token);

        // Assert
        assertEquals(TEST_EMAIL, extractedEmail);
    }

    @Test
    void testValidToken() {
        // Arrange
        String token = jwtService.getToken(TEST_EMAIL, TEST_NOMBRE);

        // Act & Assert
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void testInvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.here";

        // Act & Assert
        assertFalse(jwtService.isTokenValid(invalidToken, userDetails));
    }

    @Test
    void testInvalidTokenWithDifferentUser() {
        // Arrange
        String token = jwtService.getToken(TEST_EMAIL, TEST_NOMBRE);
        when(userDetails.getUsername()).thenReturn("diferente@example.com");

        // Act & Assert
        assertFalse(jwtService.isTokenValid(token, userDetails));
    }
}
