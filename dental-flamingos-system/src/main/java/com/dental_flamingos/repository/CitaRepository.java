package com.dental_flamingos.repository;

import com.dental_flamingos.model.Cita;
import com.dental_flamingos.model.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    @Query("SELECT c FROM Cita c WHERE c.fechaHoraCita BETWEEN :startOfDay AND :endOfDay AND c.status.idStatus = 1 AND c.paciente.dentista.idDentista = :idDentista")
    List<Cita> findCitasByDate(@Param("startOfDay") LocalDateTime startOfDay,
                               @Param("endOfDay") LocalDateTime endOfDay,
                               @Param("idDentista") Integer idDentista);

    @Modifying
    @Transactional
    @Query("UPDATE Cita c SET c.status.idStatus = 3 WHERE c.idCita = :idCita")
    int finalizaCitaById(@Param("idCita") Integer idCita);

    List<Cita> findCitasByPacienteIdPaciente(Integer idPaciente);

    @Query("SELECT c FROM Cita c WHERE " +
            "(:status IS NULL OR c.status.idStatus = :status) AND " +
            "(:fechaInicio IS NULL OR c.fechaHoraCita >= :fechaInicio) AND " +
            "(:fechaFin IS NULL OR c.fechaHoraCita <= :fechaFin) AND " +
            "(:tratamiento IS NULL OR c.tratamiento.idTratamiento = :tratamiento)")
    List<Cita> filtrarCitas(@Param("status") Integer status,
                            @Param("fechaInicio") LocalDateTime fechaInicio,
                            @Param("fechaFin") LocalDateTime fechaFin,
                            @Param("tratamiento") Integer tratamiento);

    List<Cita> findCitasByPacienteDentistaIdDentista(Integer idDentista);
}
