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
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    // Esta clase actúa como un DTO (Data Transfer Object) para transferir datos desde el cliente al servidor.
    // Los atributos se definen nuevamente aquí para:
    // 1. Aplicar validaciones específicas para los datos que se reciben en la solicitud.
    // 2. Evitar exponer directamente la entidad Usuario, protegiendo datos sensibles o irrelevantes.
    // 3. Mantener la separación de responsabilidades entre la capa de entrada (DTO) y la capa de persistencia (entidad).
    // 4. Facilitar la evolución de la API, permitiendo cambios en la estructura de la solicitud sin afectar la entidad.

    @NotBlank(message = "El campo email no puede estar vacio")
    @Email(message = "El email debe tener un formato valido")
    private String email;

    @NotBlank(message = "El campo password no puede estar vacio")
    @Size(min = 8, max = 12, message = "El password debe tener entre 8 y 12 caracteres")
    private String password;

    @NotBlank(message = "El campo dni no puede estar vacio")
    @Size(min = 9, max = 9, message = "El dni debe tener 9 caracteres")
    private String dni;

    @NotBlank(message = "El campo telefono no puede estar vacio")
    @Pattern(regexp = "\\d{9}", message = "El telefono debe tener 9 digitos")
    private String telefono;

    @NotBlank(message = "El campo nombre no puede estar vacio")
    @Size(max = 20, message = "El nombre no puede tener mas de 20 caracteres")
    private String nombre;

    @NotBlank(message = "El campo apellidos no puede estar vacio")
    @Size(max = 40, message = "Los apellidos no pueden tener mas de 40 caracteres")
    private String apellidos;

    @NotBlank(message = "El campo sede no puede estar vacio")
    @Size(max = 15, message = "La sede no puede tener mas de 15 caracteres")
    private String sede;

    @Size(min = 0, max = 24, message = "La cuenta bancaria debe tener exactamente 24 caracteres")
    private String cuenta_banco;
}