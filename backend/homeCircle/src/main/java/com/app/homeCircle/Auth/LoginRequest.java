package com.app.homeCircle.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    // Esta clase actúa como un DTO (Data Transfer Object) para manejar los datos de la solicitud de inicio de sesión.
    // Los atributos definidos aquí representan los datos que el cliente debe enviar para autenticarse.

    String email; // Correo electrónico del usuario que intenta iniciar sesión.
    String password; // Contraseña del usuario que intenta iniciar sesión.
}