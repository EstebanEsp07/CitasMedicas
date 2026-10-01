package com.clinica.citas.recordatorios.service;

import com.clinica.citas.recordatorios.domain.TareaRecordatorio;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecordatorioService {

    private final ProveedorNotificacionClient proveedorClient;
    private final RabbitTemplate rabbitTemplate;

    public RecordatorioService(ProveedorNotificacionClient proveedorClient, RabbitTemplate rabbitTemplate) {
        this.proveedorClient = proveedorClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void procesarRecordatorio(TareaRecordatorio tarea) {
        boolean exito = proveedorClient.enviarNotificacion(tarea);

        if (!exito) {
            if (tarea.getReintentos() < 3) {
                tarea.setReintentos(tarea.getReintentos() + 1);
                // Reencolar con Backoff o mandar a DLQ si excede
                rabbitTemplate.convertAndSend("notifications.direct", "recordatorio.send", tarea);
            } else {
                // Mandar a Dead Letter Queue para revisión manual
                rabbitTemplate.convertAndSend("dlx.recordatorios", "recordatorios.failed", tarea);
            }
        }
    }
}
