package com.clinica.citas.disponibilidad.controller;

import com.clinica.citas.disponibilidad.domain.SlotDisponibilidad;
import com.clinica.citas.disponibilidad.service.DisponibilidadService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<SlotDisponibilidad>> obtenerDisponibilidad(
            @PathVariable Long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        
        List<SlotDisponibilidad> slots = disponibilidadService.obtenerDisponibilidadDoctor(doctorId, fecha);
        return ResponseEntity.ok(slots);
    }
}
