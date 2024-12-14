package com.dental_flamingos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "catalogotratamientos")
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTratamiento;
    @NotEmpty(message = "El campo no puede estar vacío")
    @Size(min = 1, message = "El campo debe tener texto")
    private String descripcion;
}

