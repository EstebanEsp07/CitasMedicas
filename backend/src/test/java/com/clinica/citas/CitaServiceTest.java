package com.clinica.citas;

import com.clinica.citas.reserva.domain.Cita;
import com.clinica.citas.reserva.domain.EstadoCita;
import com.clinica.citas.reserva.repository.CitaRepository;
import com.clinica.citas.reserva.service.CitaService;
import com.clinica.citas.reserva.service.ValidadorConcurrenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CitaServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private ValidadorConcurrenciaService validadorConcurrenciaService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    private CitaService citaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        citaService = new CitaService(citaRepository, validadorConcurrenciaService, rabbitTemplate);
    }

    @Test
    void crearCitaExitosamente() {
        LocalDateTime inicio = LocalDateTime.now().plusDays(1);
        LocalDateTime fin = inicio.plusMinutes(30);

        Cita citaEsperada = Cita.builder()
                .id(1L)
                .medicoId(101L)
                .pacienteId(202L)
                .fechaHoraInicio(inicio)
                .fechaHoraFin(fin)
                .motivo("Consulta general")
                .estado(EstadoCita.RESERVADA)
                .build();

        when(citaRepository.save(any(Cita.class))).thenReturn(citaEsperada);

        Cita resultado = citaService.crearCita(101L, 202L, inicio, fin, "Consulta general");

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(EstadoCita.RESERVADA, resultado.getEstado());
        verify(validadorConcurrenciaService, times(1)).validarYBloquearSlot(101L, inicio, fin);
        verify(citaRepository, times(1)).save(any(Cita.class));
    }

    @Test
    void cancelarCitaExitosamente() {
        Cita citaExistente = Cita.builder()
                .id(1L)
                .estado(EstadoCita.RESERVADA)
                .build();

        when(citaRepository.findById(1L)).thenReturn(Optional.of(citaExistente));
        when(citaRepository.save(any(Cita.class))).thenAnswer(i -> i.getArgument(0));

        Cita resultado = citaService.cancelarCita(1L);

        assertEquals(EstadoCita.CANCELADA, resultado.getEstado());
        verify(citaRepository, times(1)).save(citaExistente);
    }
}
