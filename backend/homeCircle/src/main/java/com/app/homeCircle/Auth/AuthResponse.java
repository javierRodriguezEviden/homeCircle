package com.app.homeCircle.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    // Esta clase actúa como un DTO (Data Transfer Object) para enviar la respuesta de autenticación al cliente.
    // Contiene el token JWT generado después de un inicio de sesión o registro exitoso.

    String token; // El token JWT que se devuelve al cliente.
}