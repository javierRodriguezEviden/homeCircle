package com.app.homeCircle.Auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    // Esta clase actúa como un DTO (Data Transfer Object) para manejar los datos de
    // la solicitud de inicio de sesión.
    // Los atributos definidos aquí representan los datos que el cliente debe enviar
    // para autenticarse.

    @NotBlank(message = "El correo está vacío")
    //@Email(message = "Formato de correo inválido")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El email debe tener un dominio válido")
    String email;

    @NotBlank(message = "La contraseña está vacía")
    @Size(min = 8, max = 12, message = "La contraseña debe tener entre 8 y 12 caracteres")
    String password;
}