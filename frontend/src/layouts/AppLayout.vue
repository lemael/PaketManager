<script setup lang="ts">
import { RouterLink, useRoute, useRouter } from 'vue-router';
import { useDashboardStore } from '@/stores/useDashboardStore';
import { useAuthStore } from '@/stores/useAuthStore';

const route = useRoute();
const router = useRouter();
const dashboardStore = useDashboardStore();
const authStore = useAuthStore();

const handleAuth = async () => {
  if (authStore.isLoggedIn) {
    authStore.logout();
    await router.push('/login');
  } else {
    await router.push('/login');
  }
};

const navigation = [
  { name: 'Dashboard', to: '/dashboard' },
  { name: 'Colis', to: '/colis' },
  { name: 'Transports', to: '/transports', border: true },
  { name: 'Fahrer', to: '/fahrer' },
  { name: 'Autos', to: '/autos' },
  { name: 'Kunden', to: '/kunden' },
];
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans flex flex-col">
    <div class="flex flex-row min-h-screen flex-1">
      <!-- Sidebar -->
      <aside class="w-[272px] bg-white border-r border-gray-200 hidden md:flex flex-col">
        <!-- Logo Header -->
        <div class="h-[56px] bg-gray-900 flex items-center px-6">
          <span class="text-white text-base font-semibold">PaketManager</span>
        </div>

        <!-- Navigation Links -->
        <nav class="flex flex-col pt-4 px-4 gap-1">
          <RouterLink
            v-for="item in navigation"
            :key="item.name"
            :to="item.to"
            :class="[
              route.path === item.to
                ? 'bg-blue-600 text-white font-medium'
                : 'text-gray-500 hover:bg-gray-50 font-normal',
              item.border ? 'border border-gray-200 text-gray-700' : '',
              'px-4 py-2.5 rounded-lg text-sm flex items-center transition-colors'
            ]"
          >
            {{ item.name }}
          </RouterLink>
        </nav>
        <!-- Login / Logout -->
        <div class="mt-auto p-4 border-t border-gray-200">
          <button
            type="button"
            @click="handleAuth"
            :class="[
              authStore.isLoggedIn
                ? 'text-red-600 border border-red-200 hover:bg-red-50'
                : 'bg-blue-600 text-white hover:bg-blue-700',
              'w-full px-4 py-2.5 rounded-lg text-sm font-medium transition-colors'
            ]"
          >
            {{ authStore.isLoggedIn ? 'Ausloggen' : 'Einloggen' }}
          </button>
        </div>
      </aside>

      <!-- Main Content Container -->
      <div class="flex-1 flex flex-col">
        <!-- Navbar Header -->
        <header class="h-[56px] bg-gray-900 flex items-center justify-between md:justify-end px-6">
          <div class="flex md:hidden items-center">
            <span class="text-white font-semibold">PaketManager</span>
          </div>
          <span class="text-gray-400 text-xs md:text-sm">
            {{ dashboardStore.user.role }} ({{ dashboardStore.user.name }})
          </span>
        </header>

        <!-- Dynamic Page View Slot -->
        <main class="flex-1 p-4 md:p-6 lg:p-10">
          <slot />
        </main>
      </div>
    </div>
  </div>
</template>