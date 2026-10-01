package com.clinica.citas.reserva.service;

import com.clinica.citas.reserva.domain.EstadoCita;
import com.clinica.citas.reserva.repository.CitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ValidadorConcurrenciaService {

    private final CitaRepository citaRepository;

    public ValidadorConcurrenciaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    @Transactional
    public void validarYBloquearSlot(Long medicoId, LocalDateTime inicio, LocalDateTime fin) {
        List<EstadoCita> estadosActivos = List.of(EstadoCita.RESERVADA, EstadoCita.CONFIRMADA);
        
        var solapamientos = citaRepository.findSolapamientosConBloqueo(medicoId, inicio, fin, estadosActivos);
        if (!solapamientos.isEmpty()) {
            throw new IllegalStateException("El horario seleccionado ya no está disponible o existe una reserva concurrente.");
        }
    }
}
