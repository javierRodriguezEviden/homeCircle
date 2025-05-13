package com.app.homeCircle.Security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.homeCircle.Auth.JwtService;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component // Marca esta clase como un componente de Spring para que pueda ser inyectada
           // automáticamente.
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService; // Servicio para manejar la lógica de los tokens JWT.
    private final UserDetailsService userDetailsService; // Servicio para cargar los detalles del usuario desde la base
                                                         // de datos.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Extrae el token JWT del encabezado de la solicitud.
        final String token = getTokenFromRequest(request);
        final String email;

        String path = request.getRequestURI();
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Si no hay token en la solicitud, continúa con el siguiente filtro.
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrae el email del token JWT.
        email = jwtService.getEmailFromToken(token);

        // Si el email no es nulo y no hay autenticación en el contexto de seguridad:
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carga los detalles del usuario desde la base de datos usando el email.
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Verifica si el token es válido para el usuario cargado.
            if (jwtService.isTokenValid(token, userDetails)) {
                // Crea un objeto de autenticación para el usuario.
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // Detalles del usuario autenticado.
                        null, // No se requiere una contraseña aquí.
                        userDetails.getAuthorities() // Roles y permisos del usuario.
                );

                // Asocia los detalles de la solicitud al token de autenticación.
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continúa con el siguiente filtro en la cadena.
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT del encabezado "Authorization" de la solicitud.
     * 
     * @param request La solicitud HTTP.
     * @return El token JWT si está presente, o null si no lo está.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Verifica si el encabezado contiene un token que comienza con "Bearer ".
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Devuelve el token sin el prefijo "Bearer ".
        }

        return null; // Si no hay token, devuelve null.
    }
}