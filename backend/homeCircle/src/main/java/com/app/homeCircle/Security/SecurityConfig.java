package com.app.homeCircle.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration // Indica que esta clase es una configuración de Spring.
@EnableWebSecurity // Habilita la configuración de seguridad web de Spring Security.
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; // Filtro personalizado para validar tokens JWT.
    private final UsuarioDetailsService usuarioDetailsService; // Servicio para cargar detalles del usuario desde la base de datos.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilita la protección CSRF (no necesaria para APIs REST).
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/auth/**").permitAll() // Permite acceso público a las rutas que comienzan con /auth/**.
                        .anyRequest().authenticated()) // Requiere autenticación para cualquier otra ruta.
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura las sesiones como "stateless" (sin estado).
                .authenticationProvider(authenticationProvider()) // Configura el proveedor de autenticación.
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Añade el filtro JWT antes del filtro de autenticación por usuario/contraseña.
                .build(); // Construye y devuelve la cadena de filtros de seguridad.
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Proporciona el gestor de autenticación configurado por Spring Security.
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Configura el proveedor de autenticación basado en DAO.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(usuarioDetailsService); // Usa el servicio de detalles del usuario.
        authenticationProvider.setPasswordEncoder(passwordEncoder()); // Configura el codificador de contraseñas (BCrypt).
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Proporciona un codificador de contraseñas basado en BCrypt.
        return new BCryptPasswordEncoder();
    }
}