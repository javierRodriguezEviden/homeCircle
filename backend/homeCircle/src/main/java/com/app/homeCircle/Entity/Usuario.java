package com.app.homeCircle.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(
    name = "usuario",
    uniqueConstraints = {
        @jakarta.persistence.UniqueConstraint(columnNames = "dni"),
        @jakarta.persistence.UniqueConstraint(columnNames = "email")
    }    
)

/* Entidad del usuario */
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "El campo email no puede estar vacio")
    @Email(message = "El email debe tener un formato valido")
    //@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El email debe tener un dominio válido")
    private String email;

    @NotBlank(message = "El campo password no puede estar vacio")
    private String password;

    @NotBlank(message = "El campo dni no puede estar vacio")
    @Pattern(regexp = "\\d{8}[A-HJ-NP-TV-Z]", message = "El DNI debe tener 8 números seguidos de una letra mayúscula válida")
    private String dni;

    @NotBlank(message = "El campo telefono no puede estar vacio")
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

    /* Quitado el NotBlank ya que queremos que pueda estar vacío */
    @Size(min = 0, max = 24, message = "La cuenta bancaria debe tener exactamente 24 caracteres")
    private String cuenta_banco;

    @OneToMany(mappedBy = "usuario")
    private List<Casa> casas;

    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

}
