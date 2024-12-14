package com.dental_flamingos.repository;

import com.dental_flamingos.model.PagoMovimientos;
import com.dental_flamingos.model.PagoResumen;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoResumenRepository extends JpaRepository<PagoResumen, Integer> {

    @Query("SELECT r FROM PagoResumen r WHERE r.cita.idCita = :idCita")
    Optional<PagoResumen> obtenerResumenPorCita(@Param("idCita") Integer idCita);

    @Query("SELECT r FROM PagoResumen r WHERE r.cita.paciente.idPaciente = :idPaciente AND r.cita.idCita = :idCita")
    PagoResumen obtenerResumenPorPacienteYCita(@Param("idPaciente") Integer idPaciente, @Param("idCita") Integer idCita);

    @Modifying
    @Transactional
    @Query("UPDATE PagoResumen r SET r.montoPagado = :montoPagado WHERE r.cita.idCita = :idCita")
    int actualizarMontoPagado(@Param("montoPagado") Double montoPagado, @Param("idCita") Integer idCita);

}
