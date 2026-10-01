import React, { useEffect, useState } from 'react';
import { MetricasSistema, citasService } from '../api/citasApi';

export const DashboardMetricas: React.FC = () => {
  const [metricas, setMetricas] = useState<MetricasSistema | null>(null);

  useEffect(() => {
    citasService.getMetricas().then(setMetricas).catch(console.error);
  }, []);

  return (
    <div style={{ background: '#fff', padding: '20px' }}>
      <h2>Dashboard de Métricas</h2>
      <p>Citas Duplicadas: {metricas?.citasDuplicadas ?? 0}</p>
      <p>Tasa Ausencias: {metricas?.tasaAusenciasPct ?? 0}%</p>
      <p>Tiempo Búsqueda: {metricas?.tiempoPromedioBusquedaMs ?? 0} ms</p>
      <p>Recordatorios Entregados: {metricas?.recordatoriosEntregadosPct ?? 0}%</p>
    </div>
  );
};
