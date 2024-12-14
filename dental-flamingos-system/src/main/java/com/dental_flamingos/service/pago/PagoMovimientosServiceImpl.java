package com.dental_flamingos.service.pago;

import com.dental_flamingos.model.PagoMovimientos;
import com.dental_flamingos.repository.PagoMovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagoMovimientosServiceImpl implements PagoMovimientosService {
    @Autowired
    PagoMovimientosRepository pagoMovimientosRepository;

    @Override
    public PagoMovimientos registrarMovimiento(PagoMovimientos movimiento) {
        PagoMovimientos pagoMovimientosSaved = pagoMovimientosRepository.save(movimiento);
        return pagoMovimientosSaved;
    }

    @Override
    public List<PagoMovimientos> obtenerTodosLosMovimientos() {
        return pagoMovimientosRepository.findAll();
    }

    @Override
    public List<PagoMovimientos> obtenerMovimientosPorCita(Integer idCita) {
        return pagoMovimientosRepository.obtenerMovimientosPorCita(idCita);
    }

    @Override
    public PagoMovimientos obtenerMovimientoPorId(Integer idMovimiento) {
        PagoMovimientos pagoMovimientos = new PagoMovimientos();
        Optional<PagoMovimientos> pagoMovimientosOptional = pagoMovimientosRepository.findById(idMovimiento);
        if (pagoMovimientosOptional.isPresent()) {
            pagoMovimientos = pagoMovimientosOptional.get();
        }
        return pagoMovimientos;
    }

    @Override
    public void actualizarMovimiento(PagoMovimientos movimiento) {

    }

    @Override
    public void eliminarMovimiento(Integer idMovimiento) {

    }

    @Override
    public List<PagoMovimientos> obtenerMovimientosPorPacienteYCita(Integer idPaciente, Integer idCita) {
        return pagoMovimientosRepository.obtenerMovimientosPorPaciente(idPaciente, idCita);
    }

    @Override
    public List<PagoMovimientos> obtenerIngresosPorFechas(LocalDate inicio, LocalDate fin, Integer idDentista) {
        return pagoMovimientosRepository.obtenerIngresosPorFechas(inicio, fin, idDentista);
    }
}
