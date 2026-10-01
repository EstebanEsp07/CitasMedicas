package com.clinica.citas.disponibilidad.service;

import com.clinica.citas.disponibilidad.domain.HorarioDoctor;
import com.clinica.citas.disponibilidad.domain.SlotDisponibilidad;
import com.clinica.citas.disponibilidad.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DisponibilidadService {

    private final HorarioRepository horarioRepository;

    public DisponibilidadService(HorarioRepository horarioRepository) {
        this.horarioRepository = horarioRepository;
    }

    public List<SlotDisponibilidad> obtenerDisponibilidadDoctor(Long doctorId, LocalDate fecha) {
        List<HorarioDoctor> horarios = horarioRepository.findByDoctorId(doctorId);
        List<SlotDisponibilidad> slots = new ArrayList<>();

        for (HorarioDoctor horario : horarios) {
            LocalDateTime inicio = fecha.atTime(horario.getHoraInicio());
            LocalDateTime fin = fecha.atTime(horario.getHoraFin());

            while (inicio.plusMinutes(horario.getDuracionSlotMinutos()).isBefore(fin) || 
                   inicio.plusMinutes(horario.getDuracionSlotMinutos()).isEqual(fin)) {
                
                LocalDateTime slotFin = inicio.plusMinutes(horario.getDuracionSlotMinutos());
                
                slots.add(SlotDisponibilidad.builder()
                        .doctorId(doctorId)
                        .fechaHoraInicio(inicio)
                        .fechaHoraFin(slotFin)
                        .disponible(true)
                        .build());

                inicio = slotFin;
            }
        }
        return slots;
    }
}
