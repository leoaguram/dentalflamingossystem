package com.dental_flamingos.service.paciente;

import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.Paciente;

import java.util.List;
import java.util.Optional;

public interface PacienteService {
    List<Paciente> getPacientesPorDentista(Integer idDentista);
    Paciente guardar(Paciente paciente);
    List<Paciente> listaPacientes();
    Long getTotalPacientes();
    List<Paciente> getAll();
    boolean deletePaciente(Integer id);
    Paciente updatePaciente(Paciente paciente) throws PacienteNotFoundException;
    Paciente getById(Integer id);
}
