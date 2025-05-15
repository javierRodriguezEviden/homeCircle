package com.app.homeCircle.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "imagenCasa")
public class ImagenCasa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "El campo imagen no puede estar vacio")
    @Size(max = 50, message = "La URL de la imagen no puede tener mas de 50 caracteres")
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "id_casa")
    private Casa casa;
}
