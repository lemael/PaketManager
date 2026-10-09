import { defineStore } from 'pinia';
import { ref } from 'vue';
import { coliService } from '@/services/coli.service';
import type { Coli } from '@/services/coli.service';

export type ColiStatus = 'AUSSTEHEND' | 'IN_ARBEIT' | 'GELIEFERT' | 'ANOMALIE';



export interface ColiSummaryMetric {
  label: string;
  value: number;
}

export const useColiStore = defineStore('colis', () => {

  const colis = ref<Coli[] | null>(null);
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const metrics = ref<ColiSummaryMetric[]>([
      { label: 'Gesamtzahl der Pakete', value: 0 },
      { label: 'Wartet auf Zuweisung', value: 0 },
      { label: 'Wird gerade geliefert', value: 0 },
      { label: 'Anomalien / Verzögerungen', value: 0 },
    ]);

  const calculateMetrics = (colis: Coli[]) => {
    metrics.value = [
      { label: 'Gesamtzahl der Pakete', value: colis.length },
      { label: 'Wartet auf Zuweisung', value: colis.filter((v) => v.status === 'AUSSTEHEND').length },
      { label: 'Wird gerade geliefert', value: colis.filter((v) => v.status === 'IN_ARBEIT').length },
      { label: 'Anomalien / Verzögerungen', value: colis.filter((v) => v.status === 'ANOMALIE').length },
    ];
  };
  const fetchColis = async () => {
    loading.value = true;
    error.value = null;
    try {
      const data = await coliService.getAll();
      colis.value = data;
      calculateMetrics(data);
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Laden der Pakete.';
      console.error('Error fetching colis:', err);
    } finally {
      loading.value = false;
    }
  };

  const createColi = async (coli: Coli) => {
    loading.value = true;
    error.value = null;
    try {
      const created = await coliService.create(coli);
      colis.value = [...(colis.value ?? []), created];
      calculateMetrics(colis.value);
      return created;
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Erstellen des Pakets.';
      console.error('Error creating coli:', err);
      throw err;
    } finally {
      loading.value = false;
    }
  };

  return {
    metrics,
    colis,
    loading,
    error,
    fetchColis,
    createColi,
  };
});