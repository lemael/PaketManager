<script setup lang="ts">
import { ref, watch } from 'vue';
import type { TransportStatus } from '@/services/transport.service';

const props = defineProps<{
  open: boolean;
  submitting?: boolean;
  error?: string;
}>();

const emit = defineEmits<{
  close: [];
  submit: [payload: {
    fahrerId?: number;
    autoId?: number;
    zone: string;
    status: TransportStatus;
  }];
}>();

const form = ref({
  fahrerId: '',
  autoId: '',
  zone: '',
  status: 'IN_TRANSIT' as TransportStatus,
});

function resetForm() {
  form.value = { fahrerId: '', autoId: '', zone: '', status: 'IN_TRANSIT' };
}

watch(() => props.open, (isOpen) => {
  if (isOpen) resetForm();
});

function closeModal() {
  emit('close');
}

function submitForm() {
  emit('submit', {
    fahrerId: form.value.fahrerId ? Number(form.value.fahrerId) : undefined,
    autoId: form.value.autoId ? Number(form.value.autoId) : undefined,
    zone: form.value.zone,
    status: form.value.status,
  });
}
</script>

<template>
  <!-- Modal: Neuer Transport -->
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      @click.self="closeModal"
    >
      <div class="w-full max-w-md bg-white rounded-xl shadow-lg">
        <div class="px-6 py-4 border-b border-gray-200 flex items-center justify-between">
          <h2 class="text-base font-semibold text-gray-900">Neuer Transport</h2>
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
            <label for="transport-fahrer" class="text-sm font-medium text-gray-700">Fahrer-ID</label>
            <input
              id="transport-fahrer"
              v-model="form.fahrerId"
              type="number"
              min="1"
              placeholder="Optional"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="transport-auto" class="text-sm font-medium text-gray-700">Auto-ID</label>
            <input
              id="transport-auto"
              v-model="form.autoId"
              type="number"
              min="1"
              placeholder="Optional"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="transport-zone" class="text-sm font-medium text-gray-700">Zone *</label>
            <input
              id="transport-zone"
              v-model="form.zone"
              type="text"
              required
              placeholder="z. B. Berlin Nord"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="transport-status" class="text-sm font-medium text-gray-700">Status</label>
            <select
              id="transport-status"
              v-model="form.status"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="IN_TRANSIT">Unterwegs</option>
              <option value="PICKED_UP">Abgeholt</option>
              <option value="DELIVERED">Zugestellt</option>
              <option value="DELAYED">Verspätet</option>
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
