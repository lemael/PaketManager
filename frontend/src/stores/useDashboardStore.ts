import { defineStore } from 'pinia';
import { computed, ref } from 'vue';
import { transportService } from '@/services/transport.service';
import type { Transport } from '@/services/transport.service';
import { useAuthStore } from '@/stores/useAuthStore';




export interface DashboardStats {
  total: number;
  inProgress: number;
  delivered: number;
  pending: number;
}

export const useDashboardStore = defineStore('dashboard', () => {
  const loading = ref<boolean>(false);
  const error = ref<string | null>(null);
  const transports = ref<Transport[]>([]);
  const authStore = useAuthStore();
  const user = computed(() => ({
    name: authStore.username ?? 'Utilisateur',
    role: 'Demo',
  }));

  const stats = ref<DashboardStats>({
    total: 0,
    inProgress: 0,
    delivered: 0,
    pending: 0,
  });
   const recentTransports = (n: number) => {
    return transports.value.slice(0, n);
  };
  const calculateStats = () => {
    const total = transports.value.length;
    const inProgress = transports.value.filter(t => t.status === 'IN_TRANSIT').length;
    const delivered = transports.value.filter(t => t.status === 'DELIVERED').length;
    const pending = transports.value.filter(t => t.status === 'DELAYED').length;

    stats.value = {
      total,
      inProgress,
      delivered,
      pending,
    };
  };
 
  const fetchRecentTransports = async () => {
    try {
      loading.value = true;
      error.value = null;
      const data= await transportService.getAll();
      transports.value = data;
      calculateStats();
    } catch (err) {
      error.value = (err as Error).message;
    } finally {
      loading.value = false;
    }
  };




  return {
    user,
    stats,
    recentTransports,
    fetchRecentTransports,
  };
});