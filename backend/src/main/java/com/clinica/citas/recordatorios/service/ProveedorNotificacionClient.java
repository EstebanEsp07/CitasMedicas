package com.clinica.citas.recordatorios.service;

import com.clinica.citas.recordatorios.domain.TareaRecordatorio;
import org.springframework.stereotype.Component;

@Component
public class ProveedorNotificacionClient {

    public boolean enviarNotificacion(TareaRecordatorio tarea) {
        // Simulación de envío por proveedor (SendGrid / Twilio)
        System.out.printf("Enviando %s a %s: %s%n", tarea.getCanal(), tarea.getDestino(), tarea.getMensaje());
        return true; 
    }
}
