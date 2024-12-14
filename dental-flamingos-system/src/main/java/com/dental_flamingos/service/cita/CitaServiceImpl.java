package com.dental_flamingos.service.cita;

import com.dental_flamingos.exception.CitaNotFoundException;
import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.Cita;
import com.dental_flamingos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CitaServiceImpl implements CitaService {
    @Autowired
    CitaRepository citaRepository;

    @Autowired
    DentistaRepository dentistaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    CatalogoStatusRepository statusRepository;

    @Autowired
    TratamientoRepository tratamientoRepository;

    @Override
    public List<Cita> buscarCitas() {
        List<Cita> citas = citaRepository.findAll();
        return new ArrayList<>(citas);
    }

    @Override
    public List<Cita> buscarCitasPorDentista(Integer idDentista) {
        return citaRepository.findCitasByPacienteDentistaIdDentista(idDentista);
    }

    @Override
    public Cita guardar(Cita cita) throws PacienteNotFoundException {
        Cita citaGuardada = citaRepository.save(cita);
        return citaGuardada;
    }

    @Override
    public List<Cita> getCitasHoy(LocalDate date, Integer idDentista) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59, 999999999);
        return citaRepository.findCitasByDate(startOfDay, endOfDay, idDentista).stream().toList();
    }

    @Override
    public boolean deleteCita(Integer id) {
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            citaRepository.delete(cita.get());
            return true;
        } else
            return false;
    }

    @Override
    public Cita updateCita(Cita cita) throws CitaNotFoundException {
        Optional<Cita> citaBD = citaRepository.findById(cita.getIdCita());
        if (citaBD.isPresent()) {
            Cita modificable = citaBD.get();

            if (cita.getIdCita() != null) modificable.setIdCita(cita.getIdCita());
            if (cita.getPaciente() != null) modificable.setPaciente(cita.getPaciente());
            if (cita.getStatus() != null) modificable.setStatus(cita.getStatus());
            if (cita.getTratamiento() != null) modificable.setTratamiento(cita.getTratamiento());
            if (cita.getFechaHoraCita() != null) modificable.setFechaHoraCita(cita.getFechaHoraCita());
            if (cita.getObservaciones() != null) modificable.setObservaciones(cita.getObservaciones());
            return citaRepository.save(modificable);
        } else {
            throw new CitaNotFoundException("La cita con ID "+cita.getIdCita()+" no se encuentra");
        }
    }

    @Override
    public Cita getById(Integer id) {
        Cita cita = new Cita();
        Optional<Cita> citaBD = citaRepository.findById(id);
        if (citaBD.isPresent())
            cita = citaBD.get();
        return cita;
    }

    @Override
    public void finalizarCita(Integer idCita) {
        citaRepository.finalizaCitaById(idCita);
    }

    @Override
    public List<Cita> obtenerCitasPorPaciente(Integer idPaciente) {
        return citaRepository.findCitasByPacienteIdPaciente(idPaciente);
    }

    @Override
    public List<Cita> buscarCitasConFiltros(Integer status, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer tratamiento) {
        return citaRepository.filtrarCitas(status, fechaInicio, fechaFin, tratamiento);
    }

}
