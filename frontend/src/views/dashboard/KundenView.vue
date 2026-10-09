<script setup lang="ts">
import { onMounted, ref } from 'vue';
import NeuerKunde from '@/components/kunden/NeuerKunde.vue';
import { useKundeStore } from '@/stores/useKundeStore';

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
  name: string;
  mainContact: string;
  city: string;
  contact: string;
  monthlyVolume: string;
}) {
  formError.value = '';
  if (!payload.name.trim() || !payload.mainContact.trim() || !payload.city.trim() || !payload.contact.trim()) {
    formError.value = 'Bitte alle Pflichtfelder ausfüllen.';
    return;
  }

  submitting.value = true;
  try {
    await kundeStore.createKunde(payload);
    closeModal();
  } catch {
    formError.value = 'Kunde konnte nicht erstellt werden. Bitte später erneut versuchen.';
  } finally {
    submitting.value = false;
  }
}

onMounted(() => {
  kundeStore.fetchKunden();
});
</script>

<template>
  
    <!-- Page Title -->
    <div class="mb-8 flex items-center justify-between">
      <h1 class="text-gray-900 text-2xl md:text-[32px] font-bold leading-10 tracking-tight">
        Kundenverzeichnis
      </h1>
      <button
        class="inline-flex items-center gap-2 rounded-lg bg-gray-900 px-4 py-2.5 text-sm font-medium text-white hover:bg-gray-800 transition-colors"
        @click="openModal"
      >
        <span class="text-base leading-none">+</span>
        Neuer Kunde
      </button>
    </div>

    <!-- Summary Metrics Card -->
    <div class="bg-white border border-gray-200 rounded-xl overflow-hidden mb-8 shadow-sm">
      <div class="grid grid-cols-1 md:grid-cols-3">
        <!-- Metric 1 -->
        <div class="p-5 md:px-6 md:py-5 border-b md:border-b-0 md:border-r border-gray-200">
          <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider mb-2">
            Aktive Kunden
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ kundeStore.stats.activeKunden }}
          </div>
        </div>

        <!-- Metric 2 -->
        <div class="p-5 md:px-6 md:py-5 border-b md:border-b-0 md:border-r border-gray-200">
          <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider mb-2">
            Lieferung in diesem Monat
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ kundeStore.stats.monthlyDeliveries }}
          </div>
        </div>

        <!-- Metric 3 -->
        <div class="p-5 md:px-6 md:py-5">
          <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider mb-2">
            Kunden mit Sendungen
          </div>
          <div class="text-2xl font-semibold text-gray-900">
            {{ kundeStore.stats.kundenMitSendungen }}
          </div>
        </div>
      </div>
    </div>

    <!-- Data Table Container -->
    <div class="bg-white border border-gray-200 rounded-xl overflow-hidden shadow-sm">
      <div class="px-6 py-4 border-b border-gray-200">
        <h2 class="text-gray-900 text-base font-semibold">
          Kunden Table
        </h2>
      </div>

      <div class="overflow-x-auto">
        <div class="min-w-[600px]">
          <!-- Table Header Grid -->
          <div class="grid grid-cols-[2fr_1.5fr_1.5fr_1.5fr_1fr] px-6 py-3 bg-gray-50 border-b border-gray-200">
            <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider">
              Name / Unternehmen
            </div>
            <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider">
              Hauptkontakt
            </div>
            <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider">
              Kontakt
            </div>
            <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider">
              Stadt
            </div>
            <div class="text-[11px] font-semibold text-gray-500 uppercase tracking-wider">
              Volumen
            </div>
          </div>

          <!-- Table Body Rows Grid -->
          <div
            v-for="(kunde, index) in kundeStore.kunden"
            :key="kunde.id"
            class="grid grid-cols-[2fr_1.5fr_1.5fr_1.5fr_1fr] px-6 py-4 items-center hover:bg-gray-50/50 transition-colors"
            :class="{ 'border-b border-gray-200': index !== kundeStore.kunden.length - 1 }"
          >
            <div class="text-sm font-medium text-gray-900">
              {{ kunde.name }}
            </div>
            <div class="text-sm font-normal text-gray-700">
              {{ kunde.mainContact }}
            </div>
            <div class="text-sm font-normal text-gray-700">
              {{ kunde.contact }}
            </div>
            <div class="text-sm font-normal text-gray-700">
              {{ kunde.city }}
            </div>
            <div class="text-sm font-normal text-gray-700">
              {{ kunde.monthlyVolume }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <NeuerKunde
      :open="showModal"
      :submitting="submitting"
      :error="formError"
      @close="closeModal"
      @submit="submitForm"
    />
  
</template>