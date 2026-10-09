<script setup lang="ts">
import { ref, watch } from 'vue';
import type { AutoStatus } from '@/services/auto.service';

const props = defineProps<{
  open: boolean;
  submitting?: boolean;
  error?: string;
}>();

const emit = defineEmits<{
  close: [];
  submit: [payload: {
    plateNumber: string;
    model: string;
    maxCapacity: string;
    mileage: string;
    tuvInspection: string;
    status: AutoStatus;
  }];
}>();

const form = ref({
  plateNumber: '',
  model: '',
  maxCapacity: '',
  mileage: '',
  tuvInspection: '',
  status: 'VERFÜGBAR' as AutoStatus,
});

function resetForm() {
  form.value = { plateNumber: '', model: '', maxCapacity: '', mileage: '', tuvInspection: '', status: 'VERFÜGBAR' };
}

watch(() => props.open, (isOpen) => {
  if (isOpen) resetForm();
});

function closeModal() {
  emit('close');
}

function submitForm() {
  emit('submit', { ...form.value });
}
</script>

<template>
  <!-- Modal: Neues Auto -->
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      @click.self="closeModal"
    >
      <div class="w-full max-w-md bg-white rounded-xl shadow-lg">
        <div class="px-6 py-4 border-b border-gray-200 flex items-center justify-between">
          <h2 class="text-base font-semibold text-gray-900">Neues Auto</h2>
          <button
            class="text-gray-400 hover:text-gray-600 transition-colors"
            aria-label="Schließen"
            @click="closeModal"
          >
            ✕
          </button>
        </div>

        <form class="p-6 flex flex-col gap-4" @submit.prevent="submitForm">
          <p v-if="error" class="rounded-lg border border-red-200 bg-red-50 px-4 py-2.5 text-sm text-red-700">
            {{ error }}
          </p>

          <div class="flex flex-col gap-1.5">
            <label for="auto-plate" class="text-sm font-medium text-gray-700">Zulassung *</label>
            <input
              id="auto-plate"
              v-model="form.plateNumber"
              type="text"
              required
              placeholder="z. B. B-PM 1234"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="auto-model" class="text-sm font-medium text-gray-700">Modell *</label>
            <input
              id="auto-model"
              v-model="form.model"
              type="text"
              required
              placeholder="z. B. Mercedes Sprinter"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="auto-capacity" class="text-sm font-medium text-gray-700">Max Kapazität</label>
            <input
              id="auto-capacity"
              v-model="form.maxCapacity"
              type="text"
              placeholder="z. B. 1200 kg"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="auto-mileage" class="text-sm font-medium text-gray-700">Kilometerstand</label>
            <input
              id="auto-mileage"
              v-model="form.mileage"
              type="text"
              placeholder="z. B. 45000 km"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="auto-tuv" class="text-sm font-medium text-gray-700">TÜV-Prüfung</label>
            <input
              id="auto-tuv"
              v-model="form.tuvInspection"
              type="text"
              placeholder="z. B. 12/2026"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="auto-status" class="text-sm font-medium text-gray-700">Status</label>
            <select
              id="auto-status"
              v-model="form.status"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="VERFÜGBAR">Verfügbar</option>
              <option value="IM_DIENST">Im Dienst</option>
              <option value="INSPEKTION">Inspektion</option>
              <option value="DEFEKT">Defekt</option>
            </select>
          </div>

          <div class="flex justify-end gap-3 mt-2">
            <button
              type="button"
              class="rounded-lg border border-gray-300 px-4 py-2.5 text-sm font-medium text-gray-700 hover:bg-gray-50 transition-colors"
              @click="closeModal"
            >
              Abbrechen
            </button>
            <button
              type="submit"
              :disabled="submitting"
              class="rounded-lg bg-gray-900 px-4 py-2.5 text-sm font-medium text-white hover:bg-gray-800 transition-colors disabled:opacity-50"
            >
              {{ submitting ? 'Speichern…' : 'Speichern' }}
            </button>
          </div>
        </form>
      </div>
    </div>
  </Teleport>
</template>
