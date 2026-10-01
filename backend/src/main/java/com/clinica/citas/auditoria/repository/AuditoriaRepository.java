package com.clinica.citas.auditoria.repository;

import com.clinica.citas.auditoria.domain.RegistroAuditoriaCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriaRepository extends JpaRepository<RegistroAuditoriaCita, Long> {
    List<RegistroAuditoriaCita> findByCitaIdOrderByTimestampDesc(Long citaId);
    List<RegistroAuditoriaCita> findByUsuarioId(String usuarioId);
}
