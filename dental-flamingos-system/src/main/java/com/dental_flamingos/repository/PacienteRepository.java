package com.dental_flamingos.repository;

import com.dental_flamingos.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    List<Paciente> findPacientesByDentistaIdDentista(Integer idDentista);

}
