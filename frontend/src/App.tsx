import React, { useState } from 'react';
import { BuscarDisponibilidad } from './components/BuscarDisponibilidad';
import { ReservarCita } from './components/ReservarCita';
import { AgendaDoctor } from './components/AgendaDoctor';
import { PanelAuditoria } from './components/PanelAuditoria';
import { DashboardMetricas } from './components/DashboardMetricas';
import { SlotDisponibilidad } from './api/citasApi';

export const App: React.FC = () => {
  const [tab, setTab] = useState<'buscar' | 'agenda' | 'auditoria' | 'metricas'>('buscar');
  const [selectedSlot, setSelectedSlot] = useState<SlotDisponibilidad | null>(null);

  return (
    <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
      <h1>Sistema de Gestión de Citas Médicas</h1>
      <div style={{ display: 'flex', gap: '10px', marginBottom: '20px' }}>
        <button onClick={() => { setTab('buscar'); setSelectedSlot(null); }}>Buscar / Reservar</button>
        <button onClick={() => setTab('agenda')}>Agenda Médico</button>
        <button onClick={() => setTab('auditoria')}>Auditoría</button>
        <button onClick={() => setTab('metricas')}>Métricas</button>
      </div>

      <main>
        {tab === 'buscar' && (
          selectedSlot ? (
            <ReservarCita slot={selectedSlot} onSuccess={() => setSelectedSlot(null)} onCancel={() => setSelectedSlot(null)} />
          ) : (
            <BuscarDisponibilidad onSelectSlot={(slot) => setSelectedSlot(slot)} />
          )
        )}
        {tab === 'agenda' && <AgendaDoctor />}
        {tab === 'auditoria' && <PanelAuditoria />}
        {tab === 'metricas' && <DashboardMetricas />}
      </main>
    </div>
  );
};

export default App;
