import { defineStore } from 'pinia';
import { ref } from 'vue';
import { kundeService } from '@/services/kunde.service';
import type { Kunde } from '@/services/kunde.service';


export interface KundeStats {
  activeKunden: number;
  monthlyDeliveries: number;
  kundenMitSendungen: number;
}

export const useKundeStore = defineStore('kunden', () => {
  const kunden = ref<Kunde[]>([]);
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const stats = ref<KundeStats>({
    activeKunden: 0,
    monthlyDeliveries: 0,
    kundenMitSendungen: 0,
  });

  const calculateStats = () => {
    const activeKunden = kunden.value.length;
    const monthlyDeliveries = kunden.value.reduce((sum, kunde) => {
      const volume = parseInt(kunde.monthlyVolume.split(' ')[0] ?? '0',10);
      return sum + (isNaN(volume) ? 0 : volume);
    }, 0);
    const kundenMitSendungen = kunden.value.filter(kunde => (kunde.totalColisCount ?? 0) > 0).length;

    stats.value = {
      activeKunden,
      monthlyDeliveries,
      kundenMitSendungen,
    };
  };
  const fetchKunden = async () => {
    loading.value = true;
    error.value = null;
    try {
      const data = await kundeService.getAll();
      kunden.value = data;
      calculateStats();
      console.log('Fetched kunden:', data);
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Laden der Kunden.';
      console.error('Error fetching kunden:', err);
    } finally {
      loading.value = false;
    }
  };

  const createKunde = async (kunde: Omit<Kunde, 'id'>) => {
    loading.value = true;
    error.value = null;
    try {
      const created = await kundeService.create(kunde);
      kunden.value.push(created);
      calculateStats();
      return created;
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Erstellen des Kunden.';
      console.error('Error creating kunde:', err);
      throw err;
    } finally {
      loading.value = false;
    }
  };

  return {
    stats,
    kunden,
    loading,
    error,
    fetchKunden,
    createKunde,
    calculateStats,
  };
});

