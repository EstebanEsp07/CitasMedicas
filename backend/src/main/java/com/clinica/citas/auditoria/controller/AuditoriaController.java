package com.clinica.citas.auditoria.controller;

import com.clinica.citas.auditoria.domain.RegistroAuditoriaCita;
import com.clinica.citas.auditoria.service.AuditoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<RegistroAuditoriaCita>> obtenerHistorialCita(@PathVariable Long citaId) {
        List<RegistroAuditoriaCita> historial = auditoriaService.obtenerHistorialPorCita(citaId);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RegistroAuditoriaCita>> obtenerHistorialUsuario(@PathVariable String usuarioId) {
        List<RegistroAuditoriaCita> historial = auditoriaService.obtenerHistorialPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }
}
