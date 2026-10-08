<script setup lang="ts">
import { onMounted } from 'vue';
import TransportStatusBadge from '@/components/transport/TransportStatusBadge.vue';
import { useTransportStore } from '@/stores/useTransportStore';

const transportStore = useTransportStore();

onMounted(() => {
  transportStore.fetchTours();
});
</script>

<template>
  
    <!-- Header Title -->
    <h1 class="text-gray-900 text-2xl md:text-[28px] font-bold leading-9 mb-8">
      Touren &amp; Transporte
    </h1>

    <!-- KPI Summary Grid Container -->
    <div class="bg-white rounded-xl border border-gray-200 overflow-hidden mb-10 shadow-sm">
      <div class="grid grid-cols-2 md:grid-cols-4">
        <!-- Stat 1 -->
        <div class="p-6 md:px-8 border-r border-b md:border-b-0 border-gray-200">
          <div class="text-xs font-semibold text-gray-500 uppercase tracking-widest mb-2">
            Heutige Touren
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ transportStore.stats.todayTours }}
          </div>
        </div>

        <!-- Stat 2 -->
        <div class="p-6 md:px-8 border-b md:border-b-0 md:border-r border-gray-200">
          <div class="text-xs font-semibold text-gray-500 uppercase tracking-widest mb-2">
            Unterwegs
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ transportStore.stats.inTransit }}
          </div>
        </div>

        <!-- Stat 3 -->
        <div class="p-6 md:px-8 border-r border-gray-200">
          <div class="text-xs font-semibold text-gray-500 uppercase tracking-widest mb-2">
            Abgeschlossen
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ transportStore.stats.completed }}
          </div>
        </div>

        <!-- Stat 4 -->
        <div class="p-6 md:px-8">
          <div class="text-xs font-semibold text-gray-500 uppercase tracking-widest mb-2">
            Verspätungswarnung
          </div>
          <div class="text-2xl font-semibold text-amber-500">
            {{ transportStore.stats.delayWarnings }}
          </div>
        </div>
      </div>
    </div>

    <!-- Data Table Container -->
    <div class="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
      <div class="px-6 py-5 border-b border-gray-200">
        <h2 class="text-gray-900 text-3 font-bold">
          Transport Table
        </h2>
      </div>

      <div class="overflow-x-auto">
        <table class="w-full border-collapse text-left">
          <thead>
            <tr class="border-b border-gray-200 bg-gray-50/30">
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Transport-ID
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Fahrer
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Autos
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Zone
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Colis
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Fortschritt
              </th>
              <th class="px-6 py-3 text-xs font-semibold text-gray-500 uppercase tracking-wider">
                Status
              </th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200">
            <tr
              v-for="tour in transportStore.tours"
              :key="tour.id"
              class="hover:bg-gray-50/50 transition-colors"
            >
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.id }}
              </td>
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.fahrerName }}
              </td>
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.autoName }}
              </td>
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.zone }}
              </td>
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.colisCount }} colis
              </td>
              <td class="px-6 py-4 text-sm font-normal text-gray-900">
                {{ tour.deliveredCount }}/{{ tour.colisCount }} geliefert
              </td>
              <td class="px-6 py-4">
                <TransportStatusBadge :status="tour.status" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  
</template>