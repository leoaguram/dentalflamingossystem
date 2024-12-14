package com.dental_flamingos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPaciente;

    @ManyToOne
    @JoinColumn(name = "idDentista")
    private Dentista dentista;
    @NotEmpty(message = "El nombre no puede estar vacío")
    private String nombre;
    @Column(name = "fecha_nac")
    @NotEmpty(message = "La fecha de nacimiento no puede estar vacía")
    private String fechaNac;
    @Column(name = "numero_celular")
    @NotEmpty(message = "El número telefónico no puede estar vacío")
    @Pattern(regexp = "^\\d{10}$", message = "El formato del número telefónico no es válido")
    private String numeroCelular;
    private String email;
    @NotEmpty(message = "La dirección no puede estar vacía")
    private String direccion;
}

