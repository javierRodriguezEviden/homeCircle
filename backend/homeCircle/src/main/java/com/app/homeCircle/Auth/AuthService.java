package com.app.homeCircle.Auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Entity.Usuario;
import com.app.homeCircle.Entity.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Método para autenticar a un usuario y generar un token JWT.
     * 
     * @param request Objeto que contiene las credenciales de inicio de sesión
     *                (email y contraseña).
     * @return ResponseEntity con información del login o error.
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
            // Devuelve información del usuario y el token
            response.put("token", token);
            response.put("name", usuario.getNombre());
            response.put("apellidos", usuario.getApellidos());
            response.put("email", usuario.getEmail());
            response.put("telefono", usuario.getTelefono());
            response.put("dni", usuario.getDni());
            response.put("sede", usuario.getSede());
            response.put("cuenta_banco", usuario.getCuenta_banco());
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
     * @return ResponseEntity con información del registro o error.
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