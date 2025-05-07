package com.app.homeCircle.Reserva;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "El campo fecha de entrada no puede estar vacio")
    @Future(message = "La fecha debe estar en el futuro")
    private LocalDate fecha_ent;

    @NotBlank(message = "El campo fecha de salida no puede estar vacio")
    @Future(message = "La fecha debe estar en el futuro")
    private LocalDate fecha_sal;

    @NotBlank(message = "El campo precio no puede estar vacio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private int precio;

    @NotBlank(message = "El campo IVA no puede estar vacio")
    @Min(value = 0, message = "el IVA no puede ser negativo")
    private int iva;

    @NotBlank(message = "El campo precio total")
    @Min(value = 0, message = "el IVA no puede ser negativo")
    private int precio_total;

}
