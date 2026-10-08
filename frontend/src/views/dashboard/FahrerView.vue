<script setup lang="ts">
import { onMounted } from 'vue';
import FahrerStatusBadge from '@/components/fahrer/FahrerStatusBadge.vue';
import { useFahrerStore } from '@/stores/useFahrerStore';
const fahrerStore = useFahrerStore();

onMounted(() => {
  fahrerStore.fetchFahrer();
});
</script>

<template>
 
    <!-- Title -->
    <h1 class="text-gray-900 text-2xl md:text-[32px] font-bold leading-10 mb-10">
      Fahrermanagement
    </h1>

    <!-- KPI Summary Grid Container -->
    <div class="bg-white rounded-xl border border-gray-200 overflow-hidden mb-12 shadow-sm">
      <!-- Headers -->
      <div class="grid grid-cols-2 md:grid-cols-4 border-b border-gray-200">
        <div class="p-5 md:px-8 border-r border-b md:border-b-0 border-gray-200">
          <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Gesamtbelegschaft
          </span>
        </div>
        <div class="p-5 md:px-8 border-b md:border-b-0 md:border-r border-gray-200">
          <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Aktiv
          </span>
        </div>
        <div class="p-5 md:px-8 border-r border-gray-200">
          <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Pause
          </span>
        </div>
        <div class="p-5 md:px-8">
          <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
            Urlaub
          </span>
        </div>
      </div>

      <!-- Values -->
      <div class="grid grid-cols-2 md:grid-cols-4">
        <div class="p-5 md:px-8 border-r border-b md:border-b-0 border-gray-200">
          <span class="text-2xl font-semibold text-gray-900">
            {{ fahrerStore.stats.totalStaff }}
          </span>
        </div>
        <div class="p-5 md:px-8 border-b md:border-b-0 md:border-r border-gray-200">
          <span class="text-2xl font-semibold text-gray-900">
            {{ fahrerStore.stats.active }}
          </span>
        </div>
        <div class="p-5 md:px-8 border-r border-gray-200">
          <span class="text-2xl font-semibold text-gray-900">
            {{ fahrerStore.stats.onBreak }}
          </span>
        </div>
        <div class="p-5 md:px-8">
          <span class="text-2xl font-semibold text-amber-500">
            {{ fahrerStore.stats.onLeave }}
          </span>
        </div>
      </div>
    </div>

    <!-- Data Table Container -->
    <div class="bg-white rounded-xl border border-gray-200 overflow-hidden shadow-sm">
      <div class="px-8 py-5 border-b border-gray-200">
        <h2 class="text-gray-900 text-base font-semibold">
          Fahrer Table
        </h2>
      </div>

      <div class="overflow-x-auto">
        <div class="min-w-[600px]">
          <!-- Table Header -->
          <div class="grid grid-cols-5 px-8 py-3 bg-gray-50 border-b border-gray-200">
            <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
              Fahrer
            </span>
            <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
              Kontakt
            </span>
            <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
              Führerschein
            </span>
            <span class="text-xs font-semibold text-gray-500 uppercase tracking-wider">
              Status
            </span>
          </div>

          <!-- Table Rows -->
          <div
            v-for="(fahrer, index) in fahrerStore.fahrers"
            :key="fahrer.id"
            class="grid grid-cols-4 px-8 py-4 items-center hover:bg-gray-50/50 transition-colors"
            :class="{ 'border-b border-gray-200': index !== fahrerStore.fahrers.length - 1 }"
          >
            <span class="text-sm font-normal text-gray-900">{{ fahrer.name }}</span>
            <span class="text-sm font-normal text-gray-900">{{ fahrer.phoneNumber }}</span>
            <span class="text-sm font-normal text-gray-900">{{ fahrer.licenseClass }}</span>
            <div>
              <FahrerStatusBadge :status="fahrer.status" />
            </div>
          </div>
        </div>
      </div>
    </div>
 
</template>