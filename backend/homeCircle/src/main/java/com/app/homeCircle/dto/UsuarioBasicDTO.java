package com.app.homeCircle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioBasicDTO {
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String dni;
    private String sede;

}
