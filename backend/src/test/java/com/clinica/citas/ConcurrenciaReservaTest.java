package com.clinica.citas;

import com.clinica.citas.reserva.service.CitaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ConcurrenciaReservaTest {

    @Autowired
    private CitaService citaService;

    @Test
    void testReservaSimultaneaMismoSlotEvitaOverbooking() throws InterruptedException {
        int hilosConcurrentes = 5;
        ExecutorService executor = Executors.newFixedThreadPool(hilosConcurrentes);
        CountDownLatch latch = new CountDownLatch(1);

        AtomicInteger reservasExitosas = new AtomicInteger(0);
        AtomicInteger reservasFallidas = new AtomicInteger(0);

        Long medicoId = 1L;
        Long pacienteIdBase = 100L;
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 15, 10, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 10, 15, 10, 30);

        for (int i = 0; i < hilosConcurrentes; i++) {
            final long pacienteId = pacienteIdBase + i;
            executor.submit(() -> {
                try {
                    latch.await(); // Sincroniza el inicio simultáneo de todos los hilos
                    citaService.crearCita(medicoId, pacienteId, inicio, fin, "Prueba concurrencia");
                    reservasExitosas.incrementAndGet();
                } catch (Exception e) {
                    reservasFallidas.incrementAndGet();
                }
            });
        }

        latch.countDown(); // Libera todos los hilos
        executor.shutdown();

        // Solo 1 solicitud debe tener éxito para evitar overbooking
        assertEquals(1, reservasExitosas.get(), "Solo una reserva debe completarse exitosamente");
        assertEquals(hilosConcurrentes - 1, reservasFallidas.get(), "Las solicitudes concurrentes restantes deben rebotar");
    }
}
