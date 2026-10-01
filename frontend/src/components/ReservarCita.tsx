import React, { useState } from 'react';
import { SlotDisponibilidad, citasService } from '../api/citasApi';

interface Props {
  slot: SlotDisponibilidad;
  onSuccess: () => void;
  onCancel: () => void;
}

export const ReservarCita: React.FC<Props> = ({ slot, onSuccess, onCancel }) => {
  const [nombre, setNombre] = useState('');
  const [telefono, setTelefono] = useState('');
  const [email, setEmail] = useState('');
  const [motivo, setMotivo] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await citasService.reservarCita({
        pacienteNombre: nombre,
        pacienteTelefono: telefono,
        pacienteEmail: email,
        doctorId: slot.doctorId,
        fechaHoraInicio: slot.fechaHoraInicio,
        fechaHoraFin: slot.fechaHoraFin,
        motivoConsulta: motivo,
      });
      alert('¡Cita reservada!');
      onSuccess();
    } catch (err) {
      alert('Error al reservar.');
    }
  };

  return (
    <div style={{ background: '#fff', padding: '20px', borderRadius: '8px' }}>
      <h2>Reservar Cita con {slot.nombreDoctor}</h2>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
        <input type="text" required value={nombre} onChange={(e) => setNombre(e.target.value)} placeholder="Nombre Completo" />
        <input type="tel" required value={telefono} onChange={(e) => setTelefono(e.target.value)} placeholder="Teléfono" />
        <input type="email" required value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" />
        <textarea required value={motivo} onChange={(e) => setMotivo(e.target.value)} placeholder="Motivo consulta" />
        <button type="submit">Confirmar Reserva</button>
        <button type="button" onClick={onCancel}>Cancelar</button>
      </form>
    </div>
  );
};
