package com.app.homeCircle.AuthTest;

import java.util.HashMap;
import java.util.Map;

import com.app.homeCircle.Auth.AuthController;
import com.app.homeCircle.Auth.AuthService;
import com.app.homeCircle.Auth.LoginRequest;
import com.app.homeCircle.Auth.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginExitoso() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "El correo no está registrado.");

        when(authService.login(any(LoginRequest.class))).thenReturn(ResponseEntity.badRequest().body(response));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El correo no está registrado."));
    }

    @Test
    void testLoginCredencialesIncorrectas() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Credenciales inválidas.");

        when(authService.login(any(LoginRequest.class))).thenReturn(ResponseEntity.badRequest().body(response));

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"wrong@example.com\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("La contraseña debe tener entre 8 y 12 caracteres"));

    }

    @Test
    void testRegistroExitoso() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Registro exitoso.");
        response.put("token", "mocked-jwt-token");

        when(authService.register(any(RegisterRequest.class))).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"new@example.com\", \"password\":\"StrongP12\","
                                + "\"nombre\":\"Test\", \"apellidos\":\"Usuario\", \"dni\":\"12345678A\","
                                + "\"telefono\":\"123456789\", \"sede\":\"Madrid\"}")) // Ajustar la contraseña dentro del rango correcto
                .andExpect(status().isCreated()) // Esperar código 201
                .andExpect(jsonPath("$.message").value("Usuario registrado correctamente"))
                .andExpect(jsonPath("$.token").exists()) // Validar que el campo "token" exista
                .andExpect(jsonPath("$.token").isString()); // Validar que el token sea una cadena
    }

    @Test
    void testRegistroDatosInvalidos() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("errors", new String[]{"El correo no es válido."});

        when(authService.register(any(RegisterRequest.class))).thenReturn(ResponseEntity.badRequest().body(response));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"invalid-email\", \"password\":\"short\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("El email debe tener un formato valido"))
                .andExpect(jsonPath("$.password").value("El password debe tener entre 8 y 12 caracteres"));

    }
}
