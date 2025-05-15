package com.app.homeCircle.AuthTest;

import com.app.homeCircle.Auth.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService; // Inyectamos la instancia real de JwtService

    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = jwtService.getToken("test@example.com"); // Generamos un token de prueba
    }

    @Test
    void testGenerarToken() {
        assertNotNull(token); // Verificar que el token generado no es nulo
        assertFalse(token.isEmpty()); // Verificar que el token tiene contenido
    }

    @Test
    void testExtraerEmailDesdeToken() {
        String email = jwtService.getEmailFromToken(token);
        assertEquals("test@example.com", email); // Verificar que el email es correcto
    }

    @Test
    void testValidarTokenCorrecto() {
        UserDetails user = new User("test@example.com", "password", new ArrayList<>());
        assertTrue(jwtService.isTokenValid(token, user)); // El token debe ser válido para el usuario
    }

    @Test
    void testTokenExpirado() {
        assertFalse(jwtService.isTokenExpired(token)); // Un token recién generado no debe estar expirado
    }
}
