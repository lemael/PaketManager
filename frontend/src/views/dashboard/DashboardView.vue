<script setup lang="ts">
import { onMounted } from 'vue';
import StatCard from '@/components/StatCard.vue';
import StatusBadge from '@/components/StatusBadge.vue';
import { useDashboardStore } from '@/stores/useDashboardStore';

const store = useDashboardStore();

onMounted(() => {
  store.fetchRecentTransports();
});
</script>

<template>

    <!-- Page Title -->
    <div class="mb-8">
      <h1 class="text-gray-900 text-2xl md:text-[28px] leading-9 font-bold">
        Verkehr heute
      </h1>
    </div>

    <!-- Stats Grid -->
    <div class="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-10">
      <StatCard :value="store.stats.total" label="Total" />
      <StatCard :value="store.stats.inProgress" label="In Arbeit" />
      <StatCard :value="store.stats.delivered" label="Geliefert" />
      <StatCard :value="store.stats.pending" label="ausstehend" />
    </div>

    <!-- Recent Transports Section -->
    <div>
      <div class="mb-6">
        <h2 class="text-gray-900 text-xl font-bold">
          Liste der kürzlichen Transporte
        </h2>
      </div>

      <!-- Table Container -->
      <div class="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
        <!-- Table Header -->
        <div class="grid grid-cols-3 px-6 py-4 border-b border-gray-200 bg-gray-50/50">
          <span class="text-xs font-bold uppercase tracking-wider text-gray-900">
            Transport-ID
          </span>
          <span class="text-xs font-bold uppercase tracking-wider text-gray-900">
            Fahrer
          </span>
          <span class="text-xs font-bold uppercase tracking-wider text-gray-900">
            Status
          </span>
        </div>

        <!-- Table Rows -->
        <div
          v-for="(transport, index) in store.recentTransports(5)"
          :key="transport.id"
          class="grid grid-cols-3 px-6 py-5 items-center"
          :class="{ 'border-b border-gray-200': index !== store.recentTransports(5).length - 1 }"
        >
          <span class="text-sm text-gray-700 font-medium">{{ transport.id }}</span>
          <span class="text-sm text-gray-700">{{ transport.fahrerName }}</span>
          <div>
            <StatusBadge :status="transport.status" />
          </div>
        </div>
      </div>
    </div>
  
</template>