package com.app.homeCircle.Auth;

import java.util.HashMap;
import java.util.Map;

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
        public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
                Map<String, Object> response = new HashMap<>();
                try {
                        // Buscar el usuario por email
                        Usuario usuario = userRepository.findByEmail(request.getEmail())
                                        .orElse(null);

                        if (usuario == null) {
                                response.put("message", "El correo no está registrado.");
                                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                        }

                        // Verificar la contraseña
                        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                                response.put("message", "La contraseña es incorrecta.");
                                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                        }

                        // Generar el token JWT para el usuario autenticado.
                        String token = jwtService.getToken(usuario.getEmail());
                        response.put("token", token);
                        response.put("message", "Inicio de sesión exitoso.");
                        return new ResponseEntity<>(response, HttpStatus.OK);

                } catch (Exception e) {
                        response.put("message", "Error al iniciar sesión");
                        response.put("error", e.getMessage());
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
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
                        if (userRepository.existsByEmail(request.getEmail())) {
                                response.put("message", "El correo ya está registrado.");
                                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                        }
                        if (userRepository.existsByDni(request.getDni())) {
                                response.put("message", "El DNI ya está registrado.");
                                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                        }
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
                } catch (Exception e) {
                        // Manejo de errores genéricos
                        response.put("message", "Error al registrar el usuario");
                        response.put("error", e.getMessage());
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Código 500
                }
        }
}