<script setup lang="ts">
import { onMounted, ref } from 'vue';
import AutoStatusBadge from '@/components/autos/AutoStatusBadge.vue';
import NeuesAuto from '@/components/autos/NeuesAuto.vue';
import { useAutoStore } from '@/stores/useAutoStore';
import type { AutoStatus } from '@/services/auto.service';

const autoStore = useAutoStore();

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
  plateNumber: string;
  model: string;
  maxCapacity: string;
  mileage: string;
  tuvInspection: string;
  status: AutoStatus;
}) {
  formError.value = '';
  if (!payload.plateNumber.trim() || !payload.model.trim()) {
    formError.value = 'Bitte Zulassung und Modell ausfüllen.';
    return;
  }

  submitting.value = true;
  try {
    await autoStore.createAuto(payload);
    closeModal();
  } catch {
    formError.value = 'Fahrzeug konnte nicht erstellt werden. Bitte später erneut versuchen.';
  } finally {
    submitting.value = false;
  }
}

onMounted(() => {
  autoStore.fetchAutos();
});
</script>

<template>
 
    <!-- Page Header -->
    <div class="mb-8 flex items-center justify-between">
      <h1 class="text-gray-900 text-2xl md:text-[28px] font-bold leading-9 tracking-tight">
        FAHRZEUGFLOTTE
      </h1>
      <button
        class="inline-flex items-center gap-2 rounded-lg bg-gray-900 px-4 py-2.5 text-sm font-medium text-white hover:bg-gray-800 transition-colors"
        @click="openModal"
      >
        <span class="text-base leading-none">+</span>
        Neues Auto
      </button>
    </div>

    <!-- Fleet Stats Table Header Card -->
    <div class="mb-10 border border-gray-200 rounded overflow-hidden bg-white shadow-sm">
      <div class="grid grid-cols-2 md:grid-cols-4 border-b border-gray-200 bg-gray-50">
        <div class="px-6 py-3 border-r border-b md:border-b-0 border-gray-200">
          <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
            Gesamtflotte
          </span>
        </div>
        <div class="px-6 py-3 border-b md:border-b-0 md:border-r border-gray-200">
          <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
            Im Dienst
          </span>
        </div>
        <div class="px-6 py-3 border-r border-gray-200">
          <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
            Verfügbar
          </span>
        </div>
        <div class="px-6 py-3">
          <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
            Inspektion
          </span>
        </div>
      </div>

      <div class="grid grid-cols-2 md:grid-cols-4">
        <div class="px-6 py-4 border-r border-b md:border-b-0 border-gray-200">
          <span class="text-base font-normal text-gray-900">
            {{ autoStore.stats.totalFleet }}
          </span>
        </div>
        <div class="px-6 py-4 border-b md:border-b-0 md:border-r border-gray-200">
          <span class="text-base font-normal text-gray-900">
            {{ autoStore.stats.inService }}
          </span>
        </div>
        <div class="px-6 py-4 border-r border-gray-200">
          <span class="text-base font-normal text-gray-900">
            {{ autoStore.stats.available }}
          </span>
        </div>
        <div class="px-6 py-4">
          <span class="text-base font-normal text-amber-500">
            {{ autoStore.stats.inspection }}
          </span>
        </div>
      </div>
    </div>

    <!-- Data Table Container -->
    <div class="bg-white border border-gray-200 rounded overflow-hidden shadow-sm">
      <div class="px-6 py-4 border-b border-gray-200">
        <h2 class="text-gray-900 text-base font-semibold">
          Auto Table
        </h2>
      </div>

      <div class="overflow-x-auto">
        <div class="min-w-[600px]">
          <!-- Table Header Grid -->
          <div class="grid grid-cols-6 bg-gray-50 border-b border-gray-200">
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                Zulassung
              </span>
            </div>
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                Modell
              </span>
            </div>
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                Max Kapazität
              </span>
            </div>
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                Kilometerstand
              </span>
            </div>
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                TÜV-Prüfung
              </span>
            </div>
            <div class="px-6 py-3">
              <span class="text-xs font-semibold text-gray-900 uppercase tracking-wider">
                Status
              </span>
            </div>
          </div>

          <!-- Table Rows Grid -->
          <div
            v-for="(vehicle, index) in autoStore.autos"
            :key="vehicle.id"
            class="grid grid-cols-6 items-center hover:bg-gray-50/50 transition-colors"
            :class="{ 'border-b border-gray-200': index !== autoStore.autos!.length - 1 }"
          >
            <div class="px-6 py-4 text-sm font-normal text-gray-900">
              {{ vehicle.plateNumber }}
            </div>
            <div class="px-6 py-4 text-sm font-normal text-gray-900">
              {{ vehicle.model }}
            </div>
            <div class="px-6 py-4 text-sm font-normal text-gray-900">
              {{ vehicle.maxCapacity }}
            </div>
            <div class="px-6 py-4 text-sm font-normal text-gray-900">
              {{ vehicle.mileage }}
            </div>
            <div class="px-6 py-4 text-sm font-normal text-gray-900">
              {{ vehicle.tuvInspection }}
            </div>
            <div class="px-6 py-4">
              <AutoStatusBadge :status="vehicle.status" />
            </div>
          </div>
        </div>
      </div>
    </div>

    <NeuesAuto
      :open="showModal"
      :submitting="submitting"
      :error="formError"
      @close="closeModal"
      @submit="submitForm"
    />
</template>