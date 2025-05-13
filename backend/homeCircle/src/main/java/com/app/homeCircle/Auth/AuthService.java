package com.app.homeCircle.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service // Marca esta clase como un servicio de Spring para que pueda ser inyectada en
         // otras partes de la aplicación.
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class AuthService {

        private final AuthenticationManager authenticationManager; // Gestiona la autenticación de usuarios.
        private final UsuarioRepository userRepository; // Repositorio para interactuar con la tabla de usuarios en la
                                                        // base de datos.
        private final JwtService jwtService; // Servicio para manejar la lógica de los tokens JWT.
        private final PasswordEncoder passwordEncoder; // Codificador de contraseñas (por ejemplo, BCrypt).

        /**
         * Método para autenticar a un usuario y generar un token JWT.
         * 
         * @param request Objeto que contiene las credenciales de inicio de sesión
         *                (email y contraseña).
         * @return AuthResponse que contiene el token JWT generado.
         */
        public AuthResponse login(LoginRequest request) {
                // Autenticar al usuario con email y contraseña.
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

                // Buscar el usuario por email en la base de datos.
                Usuario usuario = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                // Generar el token JWT para el usuario autenticado.
                String token = jwtService.getToken(usuario.getEmail());
                return AuthResponse.builder()
                                .token(token) // Devuelve el token generado.
                                .build();
        }

        /**
         * Método para registrar a un nuevo usuario y generar un token JWT.
         * 
         * @param request Objeto que contiene los datos del usuario a registrar.
         * @return AuthResponse que contiene el token JWT generado.
         */
        public ResponseEntity<Map<String, Object>> register(RegisterRequest request) {
                Map<String, Object> response = new HashMap<>();
                try {
                        // Crear el usuario
                        Usuario usuario = Usuario.builder()
                                        .nombre(request.getNombre())
                                        .apellidos(request.getApellidos())
                                        .email(request.getEmail())
                                        .password(passwordEncoder.encode(request.getPassword()))
                                        .dni(request.getDni())
                                        .telefono(request.getTelefono())
                                        .sede(request.getSede())
                                        .cuenta_banco(request.getCuenta_banco())
                                        .build();

                        userRepository.save(usuario);

                        // Generar el token JWT
                        String token = jwtService.getToken(usuario.getEmail());
                        response.put("message", "Usuario registrado correctamente");
                        response.put("token", token);
                        return new ResponseEntity<>(response, HttpStatus.CREATED); // Código 201
                } catch (DataIntegrityViolationException e) {
                        // Manejo de errores de integridad (duplicados)
                        response.put("message", "Error: El correo o el teléfono ya están registrados.");
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // Código 400
                } catch (Exception e) {
                        // Manejo de errores genéricos
                        response.put("message", "Error al registrar el usuario");
                        response.put("error", e.getMessage());
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Código 500
                }
        }
}