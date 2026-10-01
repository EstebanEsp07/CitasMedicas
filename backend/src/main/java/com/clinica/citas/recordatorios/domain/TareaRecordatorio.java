package com.clinica.citas.recordatorios.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TareaRecordatorio implements Serializable {

    private Long citaId;
    private Long pacienteId;
    private String destino; // Teléfono o Email
    private String mensaje;
    private String canal; // SMS, EMAIL, WHATSAPP
    private Integer reintentos;
    private LocalDateTime fechaProgramada;
}
