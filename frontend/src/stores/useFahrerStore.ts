import { defineStore } from 'pinia';
import { ref } from 'vue';
import { fahrerService} from '@/services/fahrer.service';
import type { Fahrer } from '@/services/fahrer.service';


export interface FahrerStats {
  totalStaff: number;
  waiting: number;
  active: number;
  onBreak: number;
  onLeave: number;
}

export const useFahrerStore = defineStore('fahrer', () => {
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const fahrers = ref<Fahrer[]>([]);
  const stats = ref<FahrerStats>({
    totalStaff: 0,
    waiting: 0,
    active: 0,
    onBreak: 0,
    onLeave: 0,
  });

  const calculateStats = () => {
    const totalStaff = fahrers.value.length;
    const waiting = fahrers.value.filter((f) => f.status === 'VERFÜGBAR').length;
    const active = fahrers.value.filter((f) => f.status === 'IN_AUSLIEFERUNG').length;
    const onBreak = fahrers.value.filter((f) => f.status === 'PAUSIERT').length;
    const onLeave = fahrers.value.filter((f) => f.status === 'URLAUB').length;

    stats.value = {
      totalStaff,
      waiting,
      active,
      onBreak,
      onLeave,
    };
  };

  const fetchFahrer = async () => {
    loading.value = true;
    error.value = null;
    try {
      const data = await fahrerService.getAll();
      fahrers.value = data;
      calculateStats();
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Laden der Fahrer.';
      console.error('Error fetching fahrers:', err);
    } finally {
      loading.value = false;
    }
  };

  const createFahrer = async (fahrer: Omit<Fahrer, 'id'>) => {
    loading.value = true;
    error.value = null;
    try {
      const created = await fahrerService.create(fahrer);
      fahrers.value.push(created);
      calculateStats();
      return created;
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Erstellen des Fahrers.';
      console.error('Error creating fahrer:', err);
      throw err;
    } finally {
      loading.value = false;
    }
  };

  return {
    stats,
    fahrers,
    loading,
    error,
    fetchFahrer,
    createFahrer,
  };
});           