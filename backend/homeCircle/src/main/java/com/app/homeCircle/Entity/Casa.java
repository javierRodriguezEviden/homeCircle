package com.app.homeCircle.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "casa")
@JsonIncludeProperties({
    "id", "tipo", "direccion", "cp", "localidad", "provincia", "pais", "precio"
})
public class Casa {

    public enum Tipo_casa {
        apartamento,
        chalet,
        casa_rural,
        cabaña,
        bungalow
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "El tipo de casa no puede estar vacío")
    @Enumerated(EnumType.STRING)
    private Tipo_casa tipo;

    @NotBlank(message = "El campo direccion no puede estar vacio")
    @Size(max = 50, message = "La direccion no puede tener mas de 50 caracteres")
    private String direccion;

    @NotBlank(message = "El campo cp no puede estar vacio")
    private String cp;

    @NotBlank(message = "El campo localidad no puede estar vacio")
    @Size(max = 20, message = "La localidad no puede tener mas de 20 caracteres")
    private String localidad;

    @NotBlank(message = "El campo provincia no puede estar vacio")
    @Size(max = 20, message = "La provincia no puede tener mas de 20 caracteres")
    private String provincia;

    @NotBlank(message = "El campo pais no puede estar vacio")
    @Size(max = 20, message = "El pais no puede tener mas de 20 caracteres")
    private String pais;

    @NotNull
    private int precio;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "casa")
    private List<Reserva> reservas;

    @OneToMany(mappedBy = "casa")
    private List<ImagenCasa> imagenCasa;
}
