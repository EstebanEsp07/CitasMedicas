import React, { useState } from 'react';
import { Cita, citasService } from '../api/citasApi';

export const AgendaDoctor: React.FC = () => {
  const [doctorId, setDoctorId] = useState('');
  const [fecha, setFecha] = useState('');
  const [citas, setCitas] = useState<Cita[]>([]);

  const cargarAgenda = async () => {
    try {
      const data = await citasService.getAgendaDoctor(doctorId, fecha);
      setCitas(data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div style={{ background: '#fff', padding: '20px' }}>
      <h2>Agenda del Médico</h2>
      <input type="text" value={doctorId} onChange={(e) => setDoctorId(e.target.value)} placeholder="ID Doctor" />
      <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} />
      <button onClick={cargarAgenda}>Consultar</button>

      <ul>
        {citas.map((c) => (
          <li key={c.id}>{new Date(c.fechaHoraInicio).toLocaleTimeString()} - {c.pacienteNombre} ({c.estado})</li>
        ))}
      </ul>
    </div>
  );
};
