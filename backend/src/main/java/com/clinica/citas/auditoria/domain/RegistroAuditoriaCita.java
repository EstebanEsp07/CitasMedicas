package com.clinica.citas.auditoria.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_citas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroAuditoriaCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cita_id", nullable = false)
    private Long citaId;

    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "estado_previo")
    private String estadoPrevio;

    @Column(name = "estado_nuevo")
    private String estadoNuevo;

    @Column(name = "motivo_cambio")
    private String motivoCambio;

    @Column(name = "ip_origen")
    private String ipOrigen;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}
