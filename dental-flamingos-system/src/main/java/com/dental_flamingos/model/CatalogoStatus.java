package com.dental_flamingos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "catalogostatus")
public class CatalogoStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idStatus;
    @NotEmpty(message = "El campo no puede estar vacío")
    private String descripcion;
}

