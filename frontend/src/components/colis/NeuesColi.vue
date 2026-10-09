<script setup lang="ts">
import { ref, watch } from 'vue';
import type { Coli } from '@/services/coli.service';
import type { Kunde } from '@/services/kunde.service';

type ColiStatus = Coli['status'];

const props = defineProps<{
  open: boolean;
  submitting?: boolean;
  error?: string;
  kunden: Kunde[];
}>();

const emit = defineEmits<{
  close: [];
  submit: [payload: {
    kundeId: number;
    recipient: string;
    formatAndWeight: string;
    status: ColiStatus;
    transportId?: number;
  }];
}>();

const form = ref({
  kundeId: '',
  recipient: '',
  formatAndWeight: '',
  status: 'AUSSTEHEND' as ColiStatus,
  transportId: '',
});

function resetForm() {
  form.value = { kundeId: '', recipient: '', formatAndWeight: '', status: 'AUSSTEHEND', transportId: '' };
}

watch(() => props.open, (isOpen) => {
  if (isOpen) resetForm();
});

function closeModal() {
  emit('close');
}

function submitForm() {
  emit('submit', {
    kundeId: Number(form.value.kundeId),
    recipient: form.value.recipient,
    formatAndWeight: form.value.formatAndWeight,
    status: form.value.status,
    transportId: form.value.transportId ? Number(form.value.transportId) : undefined,
  });
}
</script>

<template>
  <!-- Modal: Neues Paket -->
  <Teleport to="body">
    <div
      v-if="open"
      class="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4"
      @click.self="closeModal"
    >
      <div class="w-full max-w-md bg-white rounded-xl shadow-lg">
        <div class="px-6 py-4 border-b border-gray-200 flex items-center justify-between">
          <h2 class="text-base font-semibold text-gray-900">Neues Paket</h2>
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
            <label for="coli-sender" class="text-sm font-medium text-gray-700">Kunde / Absender *</label>
            <select
              id="coli-sender"
              v-model="form.kundeId"
              required
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="" disabled>Bitte Kunden wählen…</option>
              <option v-for="kunde in kunden" :key="kunde.id" :value="kunde.id">
                {{ kunde.name }} ({{ kunde.city }})
              </option>
            </select>
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="coli-recipient" class="text-sm font-medium text-gray-700">Empfänger *</label>
            <input
              id="coli-recipient"
              v-model="form.recipient"
              type="text"
              required
              placeholder="z. B. Max Mustermann, Berlin"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="coli-format" class="text-sm font-medium text-gray-700">Format und Gewicht *</label>
            <input
              id="coli-format"
              v-model="form.formatAndWeight"
              type="text"
              required
              placeholder="z. B. M, 5 kg"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="coli-transport" class="text-sm font-medium text-gray-700">Transport-ID</label>
            <input
              id="coli-transport"
              v-model="form.transportId"
              type="number"
              min="1"
              placeholder="Optional"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            />
          </div>

          <div class="flex flex-col gap-1.5">
            <label for="coli-status" class="text-sm font-medium text-gray-700">Status</label>
            <select
              id="coli-status"
              v-model="form.status"
              class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
            >
              <option value="AUSSTEHEND">Ausstehend</option>
              <option value="IN_ARBEIT">In Arbeit</option>
              <option value="GELIEFERT">Geliefert</option>
              <option value="ANOMALIE">Anomalie</option>
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
