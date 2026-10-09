<script setup lang="ts">
import { ref, watch } from 'vue';

const props = defineProps<{
  open: boolean;
  submitting?: boolean;
  error?: string;
}>();

const emit = defineEmits<{
  close: [];
  submit: [payload: {
    name: string;
    mainContact: string;
    city: string;
    contact: string;
    monthlyVolume: string;
  }];
}>();

const form = ref({
  name: '',
  mainContact: '',
  city: '',
  contact: '',
  monthlyVolume: '',
});

function resetForm() {
  form.value = { name: '', mainContact: '', city: '', contact: '', monthlyVolume: '' };
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
  <!-- Modal: Neuer Kunde -->
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      @click.self="closeModal"
    >
      <div class="w-full max-w-md bg-white rounded-xl shadow-lg">
        <div class="px-6 py-4 border-b border-gray-200 flex items-center justify-between">
          <h2 class="text-base font-semibold text-gray-900">Neuer Kunde</h2>
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
            <label for="kunde-name" class="text-sm font-medium text-gray-700">Name / Unternehmen *</label>
            <input
              id="kunde-name"
              v-model="form.name"
              type="text"
              required
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="kunde-contact" class="text-sm font-medium text-gray-700">Hauptkontakt *</label>
            <input
              id="kunde-contact"
              v-model="form.mainContact"
              type="text"
              required
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="kunde-city" class="text-sm font-medium text-gray-700">Stadt *</label>
            <input
              id="kunde-city"
              v-model="form.city"
              type="text"
              required
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="kunde-phone" class="text-sm font-medium text-gray-700">Kontakt (Telefon) *</label>
            <input
              id="kunde-phone"
              v-model="form.contact"
              type="tel"
              required
              placeholder="z. B. +49 30 1234567"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="kunde-volume" class="text-sm font-medium text-gray-700">Monatsvolumen</label>
            <input
              id="kunde-volume"
              v-model="form.monthlyVolume"
              type="text"
              placeholder="z. B. 120 Sendungen"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
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
