package com.clinica.citas.recordatorios.listener;

import com.clinica.citas.recordatorios.domain.TareaRecordatorio;
import com.clinica.citas.recordatorios.service.RecordatorioService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RecordatorioQueueListener {

    private final RecordatorioService recordatorioService;

    public RecordatorioQueueListener(RecordatorioService recordatorioService) {
        this.recordatorioService = recordatorioService;
    }

    @RabbitListener(queues = "recordatorios.envio")
    public void recibirRecordatorio(TareaRecordatorio tarea) {
        recordatorioService.procesarRecordatorio(tarea);
    }
}
