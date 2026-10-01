import React, { useState } from 'react';
import { SlotDisponibilidad, citasService } from '../api/citasApi';

interface Props {
  onSelectSlot: (slot: SlotDisponibilidad) => void;
}

export const BuscarDisponibilidad: React.FC<Props> = ({ onSelectSlot }) => {
  const [especialidad, setEspecialidad] = useState('');
  const [fecha, setFecha] = useState('');
  const [slots, setSlots] = useState<SlotDisponibilidad[]>([]);
  const [loading, setLoading] = useState(false);

  const handleBuscar = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    try {
      const data = await citasService.buscarDisponibilidad(especialidad, fecha);
      setSlots(data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ background: '#fff', padding: '20px', borderRadius: '8px', boxShadow: '0 1px 3px rgba(0,0,0,0.1)' }}>
      <h2>Buscar Disponibilidad</h2>
      <form onSubmit={handleBuscar} style={{ display: 'flex', gap: '15px', marginBottom: '20px' }}>
        <input type="text" value={especialidad} onChange={(e) => setEspecialidad(e.target.value)} placeholder="Especialidad" />
        <input type="date" value={fecha} onChange={(e) => setFecha(e.target.value)} />
        <button type="submit" disabled={loading}>{loading ? 'Buscando...' : 'Buscar'}</button>
      </form>
      <div>
        {slots.map((slot) => (
          <div key={slot.id} style={{ border: '1px solid #ddd', margin: '5px 0', padding: '10px' }}>
            <p><strong>{slot.nombreDoctor}</strong> - {slot.especialidad}</p>
            <p>{new Date(slot.fechaHoraInicio).toLocaleString()}</p>
            <button onClick={() => onSelectSlot(slot)}>Reservar</button>
          </div>
        ))}
      </div>
    </div>
  );
};
