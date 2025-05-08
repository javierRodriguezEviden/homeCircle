package com.app.homeCircle.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.homeCircle.Usuario.Usuario;
import com.app.homeCircle.Usuario.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        // Autenticar al usuario con email y contraseña
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Buscar el usuario por email
        Usuario usuario = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Generar el token JWT
        String token = jwtService.getToken(usuario.getEmail());
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    public AuthResponse register(RegisterRequest request) {
        // Crear un nuevo usuario
        Usuario user = Usuario.builder()
            .nombre(request.getNombre())
            .apellidos(request.getApellidos())
            .email(request.getEmail())
            .dni(request.getDni())
            .telefono(request.getTelefono())
            .sede(request.getSede())
            .password(passwordEncoder.encode(request.getPassword())) // Encriptar la contraseña
            .build();

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        // Generar el token JWT
        String token = jwtService.getToken(user.getEmail());
        return AuthResponse.builder()
            .token(token)
            .build();
    }
}