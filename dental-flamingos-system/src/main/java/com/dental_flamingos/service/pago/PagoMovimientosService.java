package com.dental_flamingos.service.pago;

import com.dental_flamingos.model.PagoMovimientos;

import java.time.LocalDate;
import java.util.List;

public interface PagoMovimientosService {
    // Crear un nuevo movimiento de pago
    PagoMovimientos registrarMovimiento(PagoMovimientos movimiento);

    // Obtener todos los movimientos
    List<PagoMovimientos> obtenerTodosLosMovimientos();

    // Obtener movimientos por ID de cita
    List<PagoMovimientos> obtenerMovimientosPorCita(Integer idCita);

    // Obtener un movimiento específico por su ID
    PagoMovimientos obtenerMovimientoPorId(Integer idMovimiento);

    // Actualizar un movimiento existente
    void actualizarMovimiento(PagoMovimientos movimiento);

    // Eliminar un movimiento por su ID
    void eliminarMovimiento(Integer idMovimiento);

    List<PagoMovimientos> obtenerMovimientosPorPacienteYCita(Integer idPaciente, Integer idCita);

    List<PagoMovimientos> obtenerIngresosPorFechas(LocalDate inicio, LocalDate fin, Integer idDentista);
}
