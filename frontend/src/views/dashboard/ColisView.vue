<script setup lang="ts">
import { onMounted, ref } from 'vue';
import ColiStatusBadge from '@/components/colis/ColisStatusBadge.vue';
import NeuesColi from '@/components/colis/NeuesColi.vue';
import { useColiStore } from '@/stores/useColiStore';
import { useKundeStore } from '@/stores/useKundeStore';
import type { Coli } from '@/services/coli.service';

const coliStore = useColiStore();
const kundeStore = useKundeStore();

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
  kundeId: number;
  recipient: string;
  formatAndWeight: string;
  status: Coli['status'];
  transportId?: number;
}) {
  formError.value = '';
  if (!payload.kundeId || !payload.recipient.trim() || !payload.formatAndWeight.trim()) {
    formError.value = 'Bitte alle Pflichtfelder ausfüllen.';
    return;
  }

  submitting.value = true;
  try {
    await coliStore.createColi(payload);
    closeModal();
  } catch {
    formError.value = 'Paket konnte nicht erstellt werden. Bitte später erneut versuchen.';
  } finally {
    submitting.value = false;
  }
}

onMounted(() => {
  coliStore.fetchColis();
  kundeStore.fetchKunden();
});
</script>

<template>
  
    <!-- Page Header -->
    <div class="mb-8 flex items-center justify-between">
      <h1 class="text-gray-900 text-2xl md:text-[28px] font-bold leading-9 tracking-tight">
        Paketverwaltung
      </h1>
      <button
        class="inline-flex items-center gap-2 rounded-lg bg-gray-900 px-4 py-2.5 text-sm font-medium text-white hover:bg-gray-800 transition-colors"
        @click="openModal"
      >
        <span class="text-base leading-none">+</span>
        Neues Paket
      </button>
    </div>

    <!-- Summary Metrics Card -->
    <div class="bg-white border border-gray-200 rounded-lg overflow-hidden mb-10 shadow-sm">
      <!-- Card Header -->
      <div class="grid grid-cols-2 border-b border-gray-200 bg-gray-50/50">
        <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
          LABEL
        </div>
        <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
          VALUE
        </div>
      </div>

      <!-- Card Metrics Rows -->
      <div class="divide-y divide-gray-200">
        <div
          v-for="item in coliStore.metrics"
          :key="item.label"
          class="grid grid-cols-2 hover:bg-gray-50/30 transition-colors"
        >
          <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
            {{ item.label }}
          </div>
          <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
            {{ item.value }}
          </div>
        </div>
      </div>
    </div>

    <!-- Data Table Header Title -->
    <div class="mb-5">
      <h2 class="text-gray-900 text-base font-bold leading-6">
        Struktur der Tabelle (ColisTable)
      </h2>
    </div>

    <!-- Data Table Container -->
    <div class="bg-white border border-gray-200 rounded-lg overflow-hidden shadow-sm">
      <div class="overflow-x-auto">
        <div class="min-w-[700px]">
          <!-- Table Header Grid -->
          <div class="grid grid-cols-6 border-b border-gray-200 bg-white">
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
              COLIS-ID
            </div>
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
              KUNDE / ABSENDER
            </div>
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
              EMPFÄNGER
            </div>
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider leading-4">
              FORMAT UND GEWICHT
            </div>
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider">
              STATUS
            </div>
            <div class="px-6 py-3.5 text-xs font-bold text-gray-900 uppercase tracking-wider leading-4">
              VERBUNDENER TRANSPORT
            </div>
          </div>

          <!-- Table Body Rows Grid -->
          <div
            v-for="(coli, index) in coliStore.colis"
            :key="coli.id"
            class="grid grid-cols-6 items-center hover:bg-gray-50/50 transition-colors"
            :class="{ 'border-b border-gray-200': index !== coliStore.colis!.length - 1 }"
          >
            <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
              {{ coli.coliNumber || coli.id }}
            </div>
            <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
              {{ coli.kundeName }}
            </div>
            <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
              {{ coli.recipient }}
            </div>
            <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
              {{ coli.formatAndWeight }}
            </div>
            <div class="px-6 py-4.5">
              <ColiStatusBadge :status="coli.status" />
            </div>
            <div class="px-6 py-4.5 text-sm font-normal text-gray-700">
              {{ coli.transportZone || '—' }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <NeuesColi
      :open="showModal"
      :submitting="submitting"
      :error="formError"
      :kunden="kundeStore.kunden"
      @close="closeModal"
      @submit="submitForm"
    />
 
</template>