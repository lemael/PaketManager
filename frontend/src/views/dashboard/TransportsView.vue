<script setup lang="ts">
import { onMounted, ref } from 'vue';
import TransportStatusBadge from '@/components/transport/TransportStatusBadge.vue';
import NeuerTransport from '@/components/transport/NeuerTransport.vue';
import { useTransportStore } from '@/stores/useTransportStore';
import type { TransportStatus } from '@/services/transport.service';

const transportStore = useTransportStore();

const showModal = ref(false);
const submitting = ref(false);
const formError = ref('');

function openModal() {
  formError.value = '';
  showModal.value = true;
}

function closeModal() {
  showModal.value = false;
}

async function submitForm(payload: {
  fahrerId?: number;
  autoId?: number;
  zone: string;
  status: TransportStatus;
}) {
  formError.value = '';
  if (!payload.zone.trim()) {
    formError.value = 'Bitte eine Zone angeben.';
    return;
  }

  submitting.value = true;
  try {
    await transportStore.createTransport({ ...payload, colisCount: 0, deliveredCount: 0 });
    closeModal();
  } catch {
    formError.value = 'Transport konnte nicht erstellt werden. Bitte später erneut versuchen.';
  } finally {
    submitting.value = false;
  }
}

onMounted(() => {
  transportStore.fetchTours();
});
</script>

<template>
  
    <!-- Header Title -->
    <div class="mb-8 flex items-center justify-between">
      <h1 class="text-gray-900 text-2xl md:text-[28px] font-bold leading-9">
        Touren &amp; Transporte
      </h1>
      <button
        class="inline-flex items-center gap-2 rounded-lg bg-gray-900 px-4 py-2.5 text-sm font-medium text-white hover:bg-gray-800 transition-colors"
        @click="openModal"
      >
        <span class="text-base leading-none">+</span>
        Neuer Transport
      </button>
    </div>

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

    <NeuerTransport
      :open="showModal"
      :submitting="submitting"
      :error="formError"
      @close="closeModal"
      @submit="submitForm"
    />
  
</template>