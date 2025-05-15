package com.app.homeCircle.Auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController // Marca esta clase como un controlador REST, lo que permite manejar solicitudes
                // HTTP.
@RequestMapping("/auth") // Define la ruta base para todas las solicitudes manejadas por este
                         // controlador.
@RequiredArgsConstructor // Genera un constructor con los campos final para inyección de dependencias.
public class AuthController {

    private final AuthService authService; // Servicio que contiene la lógica de autenticación y registro.

    /**
     * Endpoint para iniciar sesión.
     * 
     * @param request Objeto que contiene las credenciales de inicio de sesión
     *                (email y contraseña).
     * @return Una respuesta HTTP con el token JWT generado.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request,
            org.springframework.validation.BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new java.util.HashMap<>();
            response.put("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .toArray());
            return ResponseEntity.badRequest().body(response);
        }
        // Solo llama al servicio si no hay errores de validación
        return authService.login(request);
    }

    /**
     * Endpoint para registrar un nuevo usuario.
     * 
     * @param request Objeto que contiene los datos del usuario a registrar.
     * @return Una respuesta HTTP con el token JWT generado.
     */
    @PostMapping("/register") // Define que este método maneja solicitudes POST en la ruta /auth/register.
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        // Llama al servicio de autenticación para manejar el registro y devuelve la
        // respuesta.
        return authService.register(request);
    }
}