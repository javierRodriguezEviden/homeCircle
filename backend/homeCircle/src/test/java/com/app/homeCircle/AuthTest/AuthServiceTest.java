package com.app.homeCircle.AuthTest;

import com.app.homeCircle.Auth.JwtService;
import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

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

    @Test
    public void testLoginExitoso() throws Exception {
        // Simular un usuario existente en la base de datos con su correo y contraseña
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com"); // Asignar correo de prueba
        usuario.setPassword("encodedPassword"); // Asignar una contraseña simulada

        // Cuando se busca este correo en el repositorio, devolver el usuario simulado
        Mockito.when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(usuario));

        // Simular que la contraseña ingresada coincide con la almacenada
        Mockito.when(passwordEncoder.matches("password123", usuario.getPassword()))
                .thenReturn(true);

        // Simular la generación de un token JWT
        Mockito.when(jwtService.getToken("test@example.com"))
                .thenReturn("mocked-jwt-token");

        // Enviar una solicitud POST a "/auth/login" con credenciales válidas y validar respuesta
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON) // Indicar que el contenido es JSON
                        .content("{\"email\":\"test@example.com\", \"password\":\"password123\"}")) // Cuerpo de la solicitud
                .andExpect(status().isOk()) // Esperar un estado HTTP 200 (éxito)
                .andExpect(jsonPath("$.message").value("Inicio de sesión exitoso.")) // Validar mensaje en la respuesta JSON
                .andExpect(jsonPath("$.token").value("mocked-jwt-token")); // Validar que el token se devuelve correctamente
    }

    @Test
    public void testLoginCorreoNoRegistrado() throws Exception {
        // Simular que el correo buscado no existe en la base de datos
        Mockito.when(userRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        // Enviar solicitud con un correo que no está registrado
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"unknown@example.com\", \"password\":\"password123\"}"))
                .andExpect(status().isBadRequest()) // Esperar un estado HTTP 400 (error)
                .andExpect(jsonPath("$.message").value("El correo no está registrado.")); // Validar mensaje en la respuesta
    }

    @Test
    public void testLoginContraseñaIncorrecta() throws Exception {
        // Simular un usuario existente
        Usuario usuario = new Usuario();
        usuario.setEmail("test@example.com");
        usuario.setPassword("encodedPassword");

        // Simular que el usuario es encontrado en el repositorio
        Mockito.when(userRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(usuario));

        // Simular que la contraseña ingresada es incorrecta
        Mockito.when(passwordEncoder.matches("wrongPassword", usuario.getPassword()))
                .thenReturn(false);

        // Enviar solicitud con contraseña incorrecta
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@example.com\", \"password\":\"wrongPassword\"}"))
                .andExpect(status().isBadRequest()) // Esperar estado HTTP 400 (error)
                .andExpect(jsonPath("$.errors[0]").value("La contraseña debe tener entre 8 y 12 caracteres")); // Validar mensaje en la respuesta
    }
}
