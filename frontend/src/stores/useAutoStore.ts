import { autoService } from '@/services/auto.service';
import type { Auto } from '@/services/auto.service';
import { defineStore } from 'pinia';
import { ref } from 'vue';


export interface AutoStats {
  totalFleet: number;
  inService: number;
  available: number;
  inspection: number;
}
export const useAutoStore = defineStore('autos', () => {

  const autos = ref<Auto[] | null>(null);
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const stats = ref<AutoStats>({
    totalFleet: 0,
    inService: 0,
    available: 0,
    inspection: 0,
  });

  const calculateStats = (autos: Auto[]) => {
    stats.value = {
      totalFleet: autos.length,
      inService: autos.filter((v) => v.status === 'IM_DIENST').length,
      available: autos.filter((v) => v.status === 'VERFÜGBAR').length,
      inspection: autos.filter((v) => v.status === 'INSPEKTION').length,
    };
  };

  const fetchAutos = async () => {
    loading.value = true;
    error.value = null;
    try {
      const data = await autoService.getAll();
      autos.value = data;
      calculateStats(data);
    }catch (err: any) {
      error.value = err.message || 'Fehler beim Laden der Fahrzeuge.';
      console.error('Error fetching autos:', err);
    } finally {
      loading.value = false;
    }
  };

  const createAuto = async (auto: Omit<Auto, 'id'>) => {
    loading.value = true;
    error.value = null;
    try {
      const created = await autoService.create(auto);
      autos.value = [...(autos.value ?? []), created];
      calculateStats(autos.value);
      return created;
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Erstellen des Fahrzeugs.';
      console.error('Error creating auto:', err);
      throw err;
    } finally {
      loading.value = false;
    }
  };

  return {
    stats,
    autos,
    loading,
    error,
    fetchAutos,
    createAuto,
  };
});