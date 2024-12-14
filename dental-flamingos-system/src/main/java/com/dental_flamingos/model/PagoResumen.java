package com.dental_flamingos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pagos_resumen")
public class PagoResumen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resumen")
    private Integer idResumen;

    @OneToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "monto_pagado")
    private Double montoPagado;

    @Column(name = "monto_restante", insertable = false, updatable = false)
    private Double montoRestante;

    @OneToOne
    @JoinColumn(name = "id_status")
    private CatalogoStatus status;
}
