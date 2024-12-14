package com.dental_flamingos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PagoDTO {
    private Integer idCita;
    private Double montoTotal;
    private Double montoPago;
    private String metodoPago;
    private String comentarios;
}
