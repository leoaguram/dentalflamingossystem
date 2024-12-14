package com.dental_flamingos.service.pago;

import com.dental_flamingos.exception.PagoNotFoundException;
import com.dental_flamingos.model.PagoResumen;

import java.util.List;

public interface PagoResumenService {
    // Crear un nuevo resumen de pagos
    PagoResumen registrarResumen(PagoResumen resumen);

    // Obtener todos los resúmenes de pagos
    PagoResumen obtenerTodosLosResumenes();

    // Obtener un resumen específico por su ID
    PagoResumen obtenerResumenPorId(Integer idResumen);

    // Obtener un resumen por ID de cita
    PagoResumen obtenerResumenPorCita(Integer idCita);

    // Actualizar un resumen existente
    boolean actualizarResumen(PagoResumen resumen);

    // Eliminar un resumen por su ID
    boolean eliminarResumen(Integer idResumen);

    PagoResumen obtenerResumenPorPacienteYCita(Integer idPaciente, Integer idCita);

}
