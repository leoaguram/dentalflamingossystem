package com.dental_flamingos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCita;

    @ManyToOne
    @JoinColumn(name = "idPaciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idTratamiento")
    private Tratamiento tratamiento;

    @ManyToOne
    @JoinColumn(name = "idStatus")
    private CatalogoStatus status;

    @Column(name = "fecha_hora_cita")
    private LocalDateTime fechaHoraCita;

    private String observaciones;
}
