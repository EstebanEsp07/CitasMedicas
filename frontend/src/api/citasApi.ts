import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  headers: { 'Content-Type': 'application/json' },
});

export interface SlotDisponibilidad {
  id: string;
  doctorId: string;
  nombreDoctor: string;
  especialidad: string;
  fechaHoraInicio: string;
  fechaHoraFin: string;
  disponible: boolean;
}

export interface Cita {
  id?: string;
  pacienteNombre: string;
  pacienteTelefono: string;
  pacienteEmail: string;
  doctorId: string;
  nombreDoctor?: string;
  especialidad?: string;
  fechaHoraInicio: string;
  fechaHoraFin: string;
  motivoConsulta: string;
  estado?: 'RESERVADA' | 'CONFIRMADA' | 'CANCELADA' | 'ATENDIDO' | 'NO_ASISTIO';
}

export interface RegistroAuditoria {
  id: string;
  citaId: string;
  usuarioId: string;
  accion: 'CREADA' | 'CANCELADA' | 'REPROGRAMADA' | 'RECORDATORIO_ENVIADO';
  estadoPrevio: string;
  estadoNuevo: string;
  motivoCambio: string;
  ipOrigen: string;
  timestamp: string;
}

export interface MetricasSistema {
  citasDuplicadas: number;
  tasaAusenciasPct: number;
  tiempoPromedioBusquedaMs: number;
  recordatoriosEntregadosPct: number;
}

export const citasService = {
  buscarDisponibilidad: async (especialidad?: string, fecha?: string) => {
    const res = await api.get<SlotDisponibilidad[]>('/disponibilidad', { params: { especialidad, fecha } });
    return res.data;
  },
  reservarCita: async (cita: Cita) => {
    const res = await api.post<Cita>('/citas', cita);
    return res.data;
  },
  cancelarCita: async (id: string, motivo: string) => {
    const res = await api.post(`/citas/${id}/cancelar`, { motivo });
    return res.data;
  },
  getAgendaDoctor: async (doctorId: string, fecha: string) => {
    const res = await api.get<Cita[]>(`/doctores/${doctorId}/agenda`, { params: { fecha } });
    return res.data;
  },
  getAuditoria: async () => {
    const res = await api.get<RegistroAuditoria[]>('/auditoria');
    return res.data;
  },
  getMetricas: async () => {
    const res = await api.get<MetricasSistema>('/metricas');
    return res.data;
  },
};
