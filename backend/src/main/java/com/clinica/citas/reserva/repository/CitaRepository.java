package com.clinica.citas.reserva.repository;

import com.clinica.citas.reserva.domain.Cita;
import com.clinica.citas.reserva.domain.EstadoCita;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByPacienteId(Long pacienteId);

    List<Cita> findByMedicoIdAndFechaHoraInicioBetween(Long medicoId, LocalDateTime inicio, LocalDateTime fin);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cita c WHERE c.medicoId = :medicoId AND c.estado IN :estados " +
           "AND ((c.fechaHoraInicio < :fin) AND (c.fechaHoraFin > :inicio))")
    List<Cita> findSolapamientosConBloqueo(
            @Param("medicoId") Long medicoId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin,
            @Param("estados") List<EstadoCita> estados
    );
}
