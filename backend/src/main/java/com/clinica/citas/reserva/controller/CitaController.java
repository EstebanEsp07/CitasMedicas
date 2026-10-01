package com.clinica.citas.reserva.controller;

import com.clinica.citas.reserva.domain.Cita;
import com.clinica.citas.reserva.service.CitaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    public ResponseEntity<Cita> crearCita(
            @RequestParam Long medicoId,
            @RequestParam Long pacienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam(required = false) String motivo) {

        Cita nuevaCita = citaService.crearCita(medicoId, pacienteId, inicio, fin, motivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);
    }

    @PutMapping("/{id}/reprogramar")
    public ResponseEntity<Cita> reprogramarCita(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime nuevoInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime nuevoFin) {

        Cita citaActualizada = citaService.reprogramarCita(id, nuevoInicio, nuevoFin);
        return ResponseEntity.ok(citaActualizada);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarCita(@PathVariable Long id) {
        Cita citaCancelada = citaService.cancelarCita(id);
        return ResponseEntity.ok(citaCancelada);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<Cita>> obtenerCitasPaciente(@PathVariable Long pacienteId) {
        List<Cita> citas = citaService.obtenerCitasPaciente(pacienteId);
        return ResponseEntity.ok(citas);
    }
}
