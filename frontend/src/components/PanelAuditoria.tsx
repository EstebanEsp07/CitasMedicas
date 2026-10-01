import React, { useEffect, useState } from 'react';
import { RegistroAuditoria, citasService } from '../api/citasApi';

export const PanelAuditoria: React.FC = () => {
  const [registros, setRegistros] = useState<RegistroAuditoria[]>([]);

  useEffect(() => {
    citasService.getAuditoria().then(setRegistros).catch(console.error);
  }, []);

  return (
    <div style={{ background: '#fff', padding: '20px' }}>
      <h2>Panel de Auditoría</h2>
      <ul>
        {registros.map((r) => (
          <li key={r.id}>[{r.timestamp}] {r.accion} - Cita: {r.citaId} - IP: {r.ipOrigen}</li>
        ))}
      </ul>
    </div>
  );
};
