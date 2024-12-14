package com.dental_flamingos.service.paciente;

import com.dental_flamingos.exception.PacienteNotFoundException;
import com.dental_flamingos.model.Paciente;
import com.dental_flamingos.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {
    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> getPacientesPorDentista(Integer idDentista) {
        return pacienteRepository.findPacientesByDentistaIdDentista(idDentista);
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        Paciente pacienteSaved = pacienteRepository.save(paciente);
        return pacienteSaved;
    }

    @Override
    public List<Paciente> listaPacientes() {
        return pacienteRepository.findAll();
    }

    @Override
    public Long getTotalPacientes() {
        return pacienteRepository.count();
    }

    @Override
    public List<Paciente> getAll() {
        return pacienteRepository.findAll();
    }

    @Override
    public boolean deletePaciente(Integer id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        if (paciente.isPresent()) {
            pacienteRepository.delete(paciente.get());
            return true;
        } else
            return false;
    }

    @Override
    public Paciente updatePaciente(Paciente paciente) throws PacienteNotFoundException {
        Optional<Paciente> pacienteBD = pacienteRepository.findById(paciente.getIdPaciente());
        if (pacienteBD.isPresent()) {
            Paciente modificable = pacienteBD.get();

            if (paciente.getIdPaciente() != null) modificable.setIdPaciente(paciente.getIdPaciente());
            if (paciente.getNombre() != null) modificable.setNombre(paciente.getNombre());
            if (paciente.getDentista() != null) modificable.setDentista(paciente.getDentista());
            if (paciente.getDireccion() != null) modificable.setDireccion(paciente.getDireccion());
            if (paciente.getEmail() != null) modificable.setEmail(paciente.getEmail());
            if (paciente.getFechaNac() != null) modificable.setFechaNac(paciente.getFechaNac());
            if (paciente.getNumeroCelular() != null) modificable.setNumeroCelular(paciente.getNumeroCelular());

            return pacienteRepository.save(modificable);
        } else {
            throw new PacienteNotFoundException("Paciente con ID"+paciente.getIdPaciente()+" no encontrado");
        }
    }

    @Override
    public Paciente getById(Integer id) {
        Paciente paciente = new Paciente();
        Optional<Paciente> pacienteBD = pacienteRepository.findById(id);
        if (pacienteBD.isPresent()) {
            paciente = pacienteBD.get();
        }
        return paciente;
    }


}
