package com.dental_flamingos.repository;

import com.dental_flamingos.model.PagoMovimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoMovimientosRepository extends JpaRepository<PagoMovimientos, Integer> {
    @Query("SELECT m FROM PagoMovimientos m WHERE m.cita.idCita = :idCita ORDER BY m.fechaPago ASC")
    List<PagoMovimientos> obtenerMovimientosPorCita(@Param("idCita") Integer idCita);

    @Query("SELECT m FROM PagoMovimientos m WHERE m.cita.paciente.idPaciente = :idPaciente AND m.cita.idCita = :idCita")
    List<PagoMovimientos> obtenerMovimientosPorPaciente(@Param("idPaciente") Integer idPaciente, @Param("idCita") Integer idCita);

    @Query("SELECT p FROM PagoMovimientos p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin AND p.cita.paciente.dentista.idDentista = :idDentista ORDER BY p.fechaPago ASC")
    List<PagoMovimientos> obtenerIngresosPorFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                                   @Param("fechaFin") LocalDate fechaFin,
                                                   @Param("idDentista") Integer idDentista);
}
