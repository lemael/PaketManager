import { defineStore } from 'pinia';
import { ref } from 'vue';
import { transportService } from '@/services/transport.service';
import type { Transport } from '@/services/transport.service';


export interface TransportTourStats {
  todayTours: number;
  inTransit: number;
  completed: number;
  delayWarnings: number;
}

export const useTransportStore = defineStore('transports', () => {
  const tours = ref<Transport[]>([]);
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const stats = ref<TransportTourStats>({
    todayTours: 0,
    inTransit: 0,
    completed: 0,
    delayWarnings: 0,
  });

    const calculateStats = () => {
    const todayTours = tours.value.length;
    const inTransit = tours.value.filter(tour => tour.status === 'IN_TRANSIT').length;
    const completed = tours.value.filter(tour => tour.status === 'DELIVERED').length;
    const delayWarnings = tours.value.filter(tour => tour.status === 'DELAYED').length;

    stats.value = {
      todayTours,
      inTransit,
      completed,
      delayWarnings,
    };
  };

  const fetchTours = async () => {
    loading.value = true;
    error.value = null;
    try {
      const data = await transportService.getAll();
      tours.value = data;
      calculateStats();
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Laden der Touren.';
      console.error('Error fetching tours:', err);
    } finally {
      loading.value = false;
    }
  };

  const createTransport = async (transport: Omit<Transport, 'id'>) => {
    loading.value = true;
    error.value = null;
    try {
      const created = await transportService.create(transport as Transport);
      tours.value.push(created);
      calculateStats();
      return created;
    } catch (err: any) {
      error.value = err.message || 'Fehler beim Erstellen des Transports.';
      console.error('Error creating transport:', err);
      throw err;
    } finally {
      loading.value = false;
    }
  };



  return {
    stats,
    tours,
    loading,
    error,
    fetchTours,
    createTransport,
  };
});