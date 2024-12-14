package com.dental_flamingos.service.cita;

import com.dental_flamingos.exception.CitaNotFoundException;
import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.Cita;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CitaService {
    List<Cita> buscarCitas();
    List<Cita> buscarCitasPorDentista(Integer idDentista);
    Cita guardar(Cita cita) throws PacienteNotFoundException;
    List<Cita> getCitasHoy(LocalDate date, Integer idDentista);
    boolean deleteCita(Integer id);
    Cita updateCita(Cita cita) throws CitaNotFoundException;
    Cita getById(Integer id);
    void finalizarCita(Integer idCita);

    List<Cita> obtenerCitasPorPaciente(Integer idPaciente);

    List<Cita> buscarCitasConFiltros(Integer status, LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer tratamiento);
}
