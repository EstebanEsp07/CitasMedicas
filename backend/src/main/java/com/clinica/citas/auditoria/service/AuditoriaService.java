package com.clinica.citas.auditoria.service;

import com.clinica.citas.auditoria.domain.RegistroAuditoriaCita;
import com.clinica.citas.auditoria.repository.AuditoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public AuditoriaService(AuditoriaRepository auditoriaRepository) {
        this.auditoriaRepository = auditoriaRepository;
    }

    @Transactional
    public RegistroAuditoriaCita registrarCambio(Long citaId, String usuarioId, String accion,
                                                 String estadoPrevio, String estadoNuevo,
                                                 String motivoCambio, String ipOrigen) {
        RegistroAuditoriaCita registro = RegistroAuditoriaCita.builder()
                .citaId(citaId)
                .usuarioId(usuarioId)
                .accion(accion)
                .estadoPrevio(estadoPrevio)
                .estadoNuevo(estadoNuevo)
                .motivoCambio(motivoCambio)
                .ipOrigen(ipOrigen)
                .build();

        return auditoriaRepository.save(registro);
    }

    public List<RegistroAuditoriaCita> obtenerHistorialPorCita(Long citaId) {
        return auditoriaRepository.findByCitaIdOrderByTimestampDesc(citaId);
    }

    public List<RegistroAuditoriaCita> obtenerHistorialPorUsuario(String usuarioId) {
        return auditoriaRepository.findByUsuarioId(usuarioId);
    }
}
