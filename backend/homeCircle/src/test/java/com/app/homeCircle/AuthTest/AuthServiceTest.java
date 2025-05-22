package com.app.homeCircle.AuthTest;

import com.app.homeCircle.Auth.AuthService;
import com.app.homeCircle.Auth.JwtService;
import com.app.homeCircle.Auth.LoginRequest;
import com.app.homeCircle.Auth.RegisterRequest;
import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceTest {

    @Autowired
    private MockMvc mockMvc; // Inyección de MockMvc para simular llamadas HTTP al controlador

    @MockitoBean
    private UsuarioRepository userRepository; // Simulación del repositorio para evitar acceso real a la base de datos

    @MockitoBean
    private PasswordEncoder passwordEncoder; // Simulación del codificador de contraseñas

    @MockitoBean
    private JwtService jwtService; // Simulación del servicio de generación de JWT

    @Autowired
    private AuthService authService;

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreValid() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("test@example.com", "password123");
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .nombre("Test")
                .password("encodedPassword")
                .build();
        String expectedToken = "jwt.token.test";

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(request.getPassword(), usuario.getPassword())).thenReturn(true);
        when(jwtService.getToken(usuario.getEmail(), usuario.getNombre())).thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(expectedToken))
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso."));
    }

    @Test
    void login_ShouldReturnBadRequest_WhenEmailNotFound() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("noexiste@example.com", "password123");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El correo no está registrado."));
    }

    @Test
    void login_ShouldReturnBadRequest_WhenPasswordIsIncorrect() throws Exception {
        // Arrange
        LoginRequest request = new LoginRequest("test@example.com", "wrongPassword");
        Usuario usuario = Usuario.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(request.getPassword(), usuario.getPassword())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("La contraseña es incorrecta."));
    }

    @Test
    void register_ShouldCreateUserAndReturnToken_WhenValidRequest() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "Test", "User", "test@example.com", "password123",
                "12345678A", "123456789", "Sede1", "ES1234567890"
        );
        String expectedToken = "jwt.token.test";

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByDni(request.getDni())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(jwtService.getToken(anyString(), anyString())).thenReturn(expectedToken);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value(expectedToken))
                .andExpect(jsonPath("$.message").value("Usuario registrado correctamente"));
    }

    @Test
    void register_ShouldReturnBadRequest_WhenEmailExists() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "Test", "User", "existing@example.com", "password123",
                "12345678A", "123456789", "Sede1", "ES1234567890"
        );

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El correo ya está registrado."));
    }

    @Test
    void register_ShouldReturnBadRequest_WhenDniExists() throws Exception {
        // Arrange
        RegisterRequest request = new RegisterRequest(
                "Test", "User", "test@example.com", "password123",
                "12345678A", "123456789", "Sede1", "ES1234567890"
        );

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(userRepository.existsByDni(request.getDni())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("El DNI ya está registrado."));
    }

}
