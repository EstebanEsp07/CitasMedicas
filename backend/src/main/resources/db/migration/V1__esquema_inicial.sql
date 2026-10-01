-- Extensiones necesarias
CREATE EXTENSION IF NOT EXISTS btree_gist;

-- Tabla de Médicos
CREATE TABLE IF NOT EXISTS medicos (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    correo VARCHAR(150) UNIQUE NOT NULL
);

-- Tabla de Pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    correo VARCHAR(150) NOT NULL
);

-- Tabla de Horarios de Doctores
CREATE TABLE IF NOT EXISTS horarios_doctor (
    id BIGSERIAL PRIMARY KEY,
    doctor_id BIGINT NOT NULL REFERENCES medicos(id),
    dia_semana VARCHAR(20) NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    duracion_slot_minutos INT NOT NULL DEFAULT 30
);

-- Tipo ENUM para Estado de Cita
CREATE TYPE estado_cita AS ENUM ('RESERVADA', 'CONFIRMADA', 'CANCELADA', 'ATENDIDO', 'NO_ASISTIO');

-- Tabla Principal de Citas
CREATE TABLE IF NOT EXISTS citas (
    id BIGSERIAL PRIMARY KEY,
    medico_id BIGINT NOT NULL REFERENCES medicos(id),
    paciente_id BIGINT NOT NULL REFERENCES pacientes(id),
    fecha_hora_inicio TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_hora_fin TIMESTAMP WITH TIME ZONE NOT NULL,
    rango_horario TSTZRANGE GENERATED ALWAYS AS (tstzrange(fecha_hora_inicio, fecha_hora_fin)) STORED,
    motivo VARCHAR(255),
    estado VARCHAR(30) NOT NULL DEFAULT 'RESERVADA',
    creado_en TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT no_overbooking EXCLUDE USING GIST (
        medico_id WITH =,
        rango_horario WITH &&
    ) WHERE (estado IN ('RESERVADA', 'CONFIRMADA'))
);

-- Tabla de Auditoría Inmutable
CREATE TABLE IF NOT EXISTS auditoria_citas (
    id BIGSERIAL PRIMARY KEY,
    cita_id BIGINT NOT NULL,
    usuario_id VARCHAR(50) NOT NULL,
    accion VARCHAR(50) NOT NULL,
    estado_previo VARCHAR(30),
    estado_nuevo VARCHAR(30),
    motivo_cambio TEXT,
    ip_origen VARCHAR(45),
    timestamp TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
