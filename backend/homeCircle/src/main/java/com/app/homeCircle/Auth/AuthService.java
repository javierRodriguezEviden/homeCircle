package com.app.homeCircle.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Usuario.Usuario;
import com.app.homeCircle.Usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service // Marca esta clase como un servicio de Spring para que pueda ser inyectada en otras partes de la aplicación.
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class AuthService {

    private final AuthenticationManager authenticationManager; // Gestiona la autenticación de usuarios.
    private final UsuarioRepository userRepository; // Repositorio para interactuar con la tabla de usuarios en la base de datos.
    private final JwtService jwtService; // Servicio para manejar la lógica de los tokens JWT.
    private final PasswordEncoder passwordEncoder; // Codificador de contraseñas (por ejemplo, BCrypt).

    /**
     * Método para autenticar a un usuario y generar un token JWT.
     * 
     * @param request Objeto que contiene las credenciales de inicio de sesión (email y contraseña).
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
    public AuthResponse register(RegisterRequest request) {
        // Crear un nuevo usuario a partir de los datos proporcionados en la solicitud.
        Usuario usuario = Usuario.builder()
                .nombre(request.getNombre()) // Establece el nombre del usuario.
                .apellidos(request.getApellidos()) // Establece los apellidos del usuario.
                .email(request.getEmail()) // Establece el email del usuario.
                .password(passwordEncoder.encode(request.getPassword())) // Encripta la contraseña antes de guardarla.
                .dni(request.getDni()) // Establece el DNI del usuario.
                .telefono(request.getTelefono()) // Establece el teléfono del usuario.
                .sede(request.getSede()) // Establece la sede del usuario.
                .cuenta_banco(request.getCuenta_banco()) // Establece la cuenta bancaria del usuario (puede ser null).
                .build();

        // Guardar el usuario en la base de datos.
        userRepository.save(usuario);

        // Generar el token JWT para el usuario registrado.
        String token = jwtService.getToken(usuario.getEmail());
        return AuthResponse.builder()
                .token(token) // Devuelve el token generado.
                .build();
    }
}