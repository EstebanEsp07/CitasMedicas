-- Insertar Médicos Demo
INSERT INTO medicos (nombre, especialidad, correo) VALUES
('Dr. Carlos Mendoza', 'Cardiología', 'carlos.mendoza@clinica.com'),
('Dra. Ana Gómez', 'Pediatría', 'ana.gomez@clinica.com'),
('Dr. Roberto Silva', 'Medicina General', 'roberto.silva@clinica.com');

-- Insertar Pacientes Demo
INSERT INTO pacientes (nombre, telefono, correo) VALUES
('Juan Pérez', '+593991234567', 'juan.perez@email.com'),
('María López', '+593998765432', 'maria.lopez@email.com');

-- Insertar Horarios de Atención Demo
INSERT INTO horarios_doctor (doctor_id, dia_semana, hora_inicio, hora_fin, duracion_slot_minutos) VALUES
(1, 'LUNES', '08:00:00', '12:00:00', 30),
(1, 'MIÉRCOLES', '14:00:00', '18:00:00', 30),
(2, 'MARTES', '09:00:00', '13:00:00', 20),
(3, 'VIERNES', '08:00:00', '16:00:00', 30);
