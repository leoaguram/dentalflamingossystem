package com.dental_flamingos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pagos_movimientos")
public class PagoMovimientos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    private Integer idMovimiento;

    @ManyToOne
    @JoinColumn(name = "id_cita")
    private Cita cita;

    private Double monto;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "metodo_pago")
    private String metodoPago;

    private String comentarios;

    public PagoMovimientos(Cita cita, Double monto, LocalDate fechaPago, String metodoPago, String comentarios) {
        this.cita = cita;
        this.monto = monto;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.comentarios = comentarios;
    }
}
