<script setup lang="ts">
import { ref, watch } from 'vue';
import type { FahrerStatus } from '@/services/fahrer.service';

const props = defineProps<{
  open: boolean;
  submitting?: boolean;
  error?: string;
}>();

const emit = defineEmits<{
  close: [];
  submit: [payload: {
    name: string;
    phoneNumber: string;
    licenseClass: string;
    status: FahrerStatus;
  }];
}>();

const form = ref({
  name: '',
  phoneNumber: '',
  licenseClass: 'B',
  status: 'VERFÜGBAR' as FahrerStatus,
});

function resetForm() {
  form.value = { name: '', phoneNumber: '', licenseClass: 'B', status: 'VERFÜGBAR' };
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
  <!-- Modal: Neuer Fahrer -->
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      @click.self="closeModal"
    >
      <div class="w-full max-w-md bg-white rounded-xl shadow-lg">
        <div class="px-6 py-4 border-b border-gray-200 flex items-center justify-between">
          <h2 class="text-base font-semibold text-gray-900">Neuer Fahrer</h2>
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
            <label for="fahrer-name" class="text-sm font-medium text-gray-700">Name *</label>
            <input
              id="fahrer-name"
              v-model="form.name"
              type="text"
              required
              placeholder="z. B. Max Mustermann"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="fahrer-phone" class="text-sm font-medium text-gray-700">Telefonnummer *</label>
            <input
              id="fahrer-phone"
              v-model="form.phoneNumber"
              type="tel"
              required
              placeholder="z. B. +49 170 1234567"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="fahrer-license" class="text-sm font-medium text-gray-700">Führerscheinklasse</label>
            <select
              id="fahrer-license"
              v-model="form.licenseClass"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="B">B</option>
              <option value="C1">C1</option>
              <option value="C">C</option>
              <option value="CE">CE</option>
            </select>
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="fahrer-status" class="text-sm font-medium text-gray-700">Status</label>
            <select
              id="fahrer-status"
              v-model="form.status"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="VERFÜGBAR">Verfügbar</option>
              <option value="IN_AUSLIEFERUNG">In Auslieferung</option>
              <option value="PAUSIERT">Pausiert</option>
              <option value="URLAUB">Urlaub</option>
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
