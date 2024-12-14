package com.dental_flamingos.service.pago;

import com.dental_flamingos.exception.PagoNotFoundException;
import com.dental_flamingos.model.PagoResumen;
import com.dental_flamingos.repository.PagoResumenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagoResumenServiceImpl implements PagoResumenService {
    @Autowired
    PagoResumenRepository pagoResumenRepository;

    @Override
    public PagoResumen registrarResumen(PagoResumen resumen) {
        PagoResumen pagoResumenSaved = pagoResumenRepository.save(resumen);
        return pagoResumenSaved;
    }

    @Override
    public PagoResumen obtenerTodosLosResumenes() {
        PagoResumen pagoResumen = new PagoResumen();
        pagoResumen = pagoResumenRepository.findAll().get(0);
        return pagoResumen;
    }

    @Override
    public PagoResumen obtenerResumenPorId(Integer idResumen) {
        PagoResumen pagoResumen = new PagoResumen();
        Optional<PagoResumen> pagoResumenOptional = pagoResumenRepository.findById(idResumen);
        if (pagoResumenOptional.isPresent()) {
            pagoResumen = pagoResumenOptional.get();
        }
        return pagoResumen;
    }

    @Override
    public PagoResumen obtenerResumenPorCita(Integer idCita) {
        PagoResumen pagoResumen = new PagoResumen();
        Optional<PagoResumen> pagoResumenOptional = pagoResumenRepository.obtenerResumenPorCita(idCita);
        if (pagoResumenOptional.isPresent()) {
            pagoResumen = pagoResumenOptional.get();
        }
        return pagoResumen;
    }

    @Override
    public boolean actualizarResumen(PagoResumen resumen) {
        int retorno = pagoResumenRepository.actualizarMontoPagado(resumen.getMontoPagado(), resumen.getCita().getIdCita());
        if (retorno == 1)
            return true;
        else
            return false;
    }

    @Override
    public boolean eliminarResumen(Integer idResumen) {
        Optional<PagoResumen> pagoResumenOptional = pagoResumenRepository.findById(idResumen);
        if (pagoResumenOptional.isPresent()) {
            pagoResumenRepository.delete(pagoResumenOptional.get());
            return true;
        } else
            return false;
    }

    @Override
    public PagoResumen obtenerResumenPorPacienteYCita(Integer idPaciente, Integer idCita) {
        return pagoResumenRepository.obtenerResumenPorPacienteYCita(idPaciente, idCita);
    }
}
