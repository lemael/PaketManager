<script setup lang="ts">
import { ref, computed } from 'vue';
import { RouterLink, useRoute, useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/useAuthStore';
import { Eye, EyeOff } from 'lucide-vue-next';

const API_URL = import.meta.env.VITE_API_BASE_URL ?? '';

const router = useRouter();
const route = useRoute();
const authStore = useAuthStore();

const username = ref('');
const password = ref('');
const error = ref('');
const loading = ref(false);

const justRegistered = computed(() => route.query.registered === '1');

function useDemoAccount() {
  username.value = 'demo';
  password.value = 'Demo1234!';
  error.value = '';
}

const showPassword = ref(false);
async function handleLogin() {
  error.value = '';

  if (!username.value.trim() || !password.value) {
    error.value = 'Bitte Benutzername und Passwort eingeben.';
    return;
  }

  loading.value = true;
  try {
    const response = await fetch(`${API_URL}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value.trim(),
        password: password.value,
      }),
    });

    if (!response.ok) {
      error.value =
        response.status === 401
          ? 'Benutzername oder Passwort ist falsch.'
          : 'Einloggen fehlgeschlagen. Bitte später erneut versuchen.';
      return;
    }

    const data = await response.json();
    authStore.setToken(data.token);
    await router.push('/dashboard');
  } catch {
    error.value = 'Der Server ist nicht erreichbar. Bitte Verbindung prüfen.';
  } finally {
    loading.value = false;
  }
}
</script>

<template>
  <div class="min-h-screen bg-gray-50 font-sans flex flex-col">
    <header class="h-[56px] bg-gray-900 flex items-center px-6">
      <span class="text-white text-base font-semibold">PaketManager</span>
    </header>

    <main class="flex-1 flex items-center justify-center p-4">
      <div class="w-full max-w-md bg-white border border-gray-200 rounded-lg shadow-sm">
        <div class="p-6 md:p-8">
          <h1 class="text-xl font-semibold text-gray-900">Einloggen</h1>
          <p class="mt-1 text-sm text-gray-500">Melde dich mit deinem Konto an.</p>

          <p
            v-if="justRegistered"
            class="mt-4 rounded-lg border border-green-200 bg-green-50 px-4 py-2.5 text-sm text-green-700"
            role="status"
          >
            Konto erstellt. Du kannst dich jetzt einloggen.
          </p>

          <form class="mt-6 flex flex-col gap-4" novalidate @submit.prevent="handleLogin">
            <div class="flex flex-col gap-1.5">
              <label for="username" class="text-sm font-medium text-gray-700">Benutzername</label>
              <input
                id="username"
                v-model="username"
                type="text"
                autocomplete="username"
                required
                class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>

            <div class="flex flex-col gap-1.5">
              <label for="password" class="text-sm font-medium text-gray-700">Passwort</label>
              <!-- Conteneur en position relative pour caler le bouton à droite -->
              <div class="relative flex items-center">
                <input
                  id="password"
                  v-model="password"
                  :type="showPassword ? 'text' : 'password'"
                  name="password"
                  autocomplete="current-password"
                  required
                  class="w-full rounded-lg border border-gray-300 px-3 py-2.5 pr-10 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
                />
                <button
                  type="button"
                  @click="showPassword = !showPassword"
                  class="absolute right-3 flex items-center justify-center text-gray-500 hover:text-gray-700 focus:outline-none"
                  :aria-label="showPassword ? 'Passwort verbergen' : 'Passwort anzeigen'"
                  :aria-pressed="showPassword"
                >
                  <EyeOff v-if="showPassword" :size="18" />
                  <Eye v-else :size="18" />
                </button>
              </div>
            </div>

            <p
              v-if="error"
              class="rounded-lg border border-red-200 bg-red-50 px-4 py-2.5 text-sm text-red-700"
              role="alert"
            >
              {{ error }}
            </p>

            <button
              type="submit"
              :disabled="loading"
              class="w-full rounded-lg bg-blue-600 px-4 py-2.5 text-sm font-medium text-white transition-colors hover:bg-blue-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              {{ loading ? 'Einloggen …' : 'Einloggen' }}
            </button>
          </form>
        </div>

        <div class="border-t border-gray-200 px-6 py-4 md:px-8 text-sm text-gray-500">
          <button
            type="button"
            @click="useDemoAccount"
            class="w-full rounded-lg border border-blue-300 bg-white px-4 py-2 text-sm font-medium text-blue-700 transition-colors hover:bg-blue-100 focus:outline-none focus:ring-2 focus:ring-blue-500"
          >
            Demo-Konto verwenden
          </button>
        </div>

        <div class="border-t border-gray-200 px-6 py-4 md:px-8 text-sm text-gray-500">
          Noch kein Konto?
          <RouterLink to="/register" class="font-medium text-blue-600 hover:text-blue-700">
            Konto erstellen
          </RouterLink>
        </div>
      </div>
    </main>
  </div>
</template>