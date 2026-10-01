package com.clinica.citas.reserva.service;

import com.clinica.citas.reserva.domain.Cita;
import com.clinica.citas.reserva.domain.EstadoCita;
import com.clinica.citas.reserva.repository.CitaRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ValidadorConcurrenciaService validadorConcurrenciaService;
    private final RabbitTemplate rabbitTemplate;

    public CitaService(CitaRepository citaRepository,
                       ValidadorConcurrenciaService validadorConcurrenciaService,
                       RabbitTemplate rabbitTemplate) {
        this.citaRepository = citaRepository;
        this.validadorConcurrenciaService = validadorConcurrenciaService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Cita crearCita(Long medicoId, Long pacienteId, LocalDateTime inicio, LocalDateTime fin, String motivo) {
        validadorConcurrenciaService.validarYBloquearSlot(medicoId, inicio, fin);

        Cita cita = Cita.builder()
                .medicoId(medicoId)
                .pacienteId(pacienteId)
                .fechaHoraInicio(inicio)
                .fechaHoraFin(fin)
                .motivo(motivo)
                .estado(EstadoCita.RESERVADA)
                .build();

        Cita citaGuardada = citaRepository.save(cita);

        // Publicar evento para programar recordatorio
        try {
            rabbitTemplate.convertAndSend("notifications.direct", "recordatorio.send", "Cita creada ID: " + citaGuardada.getId());
        } catch (Exception e) {
            // Log warning: la cita se guardó pero falló la publicación en encolado
        }

        return citaGuardada;
    }

    @Transactional
    public Cita reprogramarCita(Long citaId, LocalDateTime nuevoInicio, LocalDateTime nuevoFin) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + citaId));

        if (cita.getEstado() == EstadoCita.CANCELADA) {
            throw new IllegalStateException("No se puede reprogramar una cita cancelada.");
        }

        validadorConcurrenciaService.validarYBloquearSlot(cita.getMedicoId(), nuevoInicio, nuevoFin);

        cita.setFechaHoraInicio(nuevoInicio);
        cita.setFechaHoraFin(nuevoFin);
        return citaRepository.save(cita);
    }

    @Transactional
    public Cita cancelarCita(Long citaId) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada con ID: " + citaId));

        cita.setEstado(EstadoCita.CANCELADA);
        return citaRepository.save(cita);
    }

    public List<Cita> obtenerCitasPaciente(Long pacienteId) {
        return citaRepository.findByPacienteId(pacienteId);
    }
}
