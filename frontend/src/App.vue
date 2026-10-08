<script setup lang="ts">
import { RouterView, useRoute } from 'vue-router';
import AppLayout from '@/layouts/AppLayout.vue';
import { computed } from 'vue';
const route = useRoute();
const showLayout = computed(() => !route.meta.hideLayout);
</script>

<template>
  <AppLayout v-if="showLayout">
    <RouterView />
  </AppLayout>
  <RouterView v-else />
</template>

<style>
/* Réinitialisation globale pour s'assurer que le layout occupe toute la hauteur */
html,
body,
#app {
  margin: 0;
  padding: 0;
  height: 100%;
  width: 100%;
}
</style>