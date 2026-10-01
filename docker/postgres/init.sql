-- Habilitar extensión btree_gist para restricciones de exclusión de rangos de tiempo
CREATE EXTENSION IF NOT EXISTS btree_gist;

-- Crear esquema inicial de Citas Médicas
CREATE TABLE IF NOT EXISTS medicos (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS pacientes (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL
);

CREATE TYPE estado_cita AS ENUM ('RESERVADA', 'CONFIRMADA', 'CANCELADA', 'ATENDIDO', 'NO_ASISTIO');

CREATE TABLE IF NOT EXISTS citas (
    id SERIAL PRIMARY KEY,
    medico_id INT NOT NULL REFERENCES medicos(id),
    paciente_id INT NOT NULL REFERENCES pacientes(id),
    rango_horario TSTZRANGE NOT NULL,
    motivo VARCHAR(255),
    estado estado_cita DEFAULT 'RESERVADA',
    creado_en TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    -- Evitar solapamiento de horario para el mismo médico en citas activas
    CONSTRAINT no_overbooking EXCLUDE USING GIST (
        medico_id WITH =,
        rango_horario WITH &&
    ) WHERE (estado IN ('RESERVADA', 'CONFIRMADA'))
);

-- Tabla de Auditoría Inmutable
CREATE TABLE IF NOT EXISTS auditoria_citas (
    id SERIAL PRIMARY KEY,
    cita_id INT NOT NULL,
    usuario_id VARCHAR(50) NOT NULL,
    accion VARCHAR(50) NOT NULL,
    estado_previo VARCHAR(30),
    estado_nuevo VARCHAR(30),
    motivo_cambio TEXT,
    ip_origen VARCHAR(45),
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
