<script setup lang="ts">
import { ref } from 'vue';
import { RouterLink, useRouter } from 'vue-router';

const API_URL = import.meta.env.VITE_API_BASE_URL ?? '';
const MIN_PASSWORD_LENGTH = 8;

const router = useRouter();

const username = ref('');
const email = ref('');
const password = ref('');
const verifyPassword = ref('');
const error = ref('');
const loading = ref(false);

function validate(): string {
  if (!username.value.trim() || !email.value.trim() || !password.value || !verifyPassword.value) {
    return 'Bitte alle Felder ausfüllen.';
  }
  if (!/^\S+@\S+\.\S+$/.test(email.value.trim())) {
    return 'Bitte eine gültige E-Mail-Adresse eingeben.';
  }
  if (password.value.length < MIN_PASSWORD_LENGTH) {
    return `Das Passwort muss mindestens ${MIN_PASSWORD_LENGTH} Zeichen lang sein.`;
  }
  if (password.value !== verifyPassword.value) {
    return 'Die Passwörter stimmen nicht überein.';
  }
  return '';
}

async function handleRegister() {
  error.value = validate();
  if (error.value) return;

  loading.value = true;
  try {
    // TODO: Endpoint an dein Spring-Boot-Backend anpassen
    const response = await fetch(`${API_URL}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: username.value.trim(),
        email: email.value.trim(),
        password: password.value,
      }),
    });

    if (!response.ok) {
      error.value =
        response.status === 409
          ? 'Benutzername oder E-Mail ist bereits vergeben.'
          : 'Konto konnte nicht erstellt werden. Bitte später erneut versuchen.';
      return;
    }

    await router.push({ path: '/login', query: { registered: '1' } });
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
          <h1 class="text-xl font-semibold text-gray-900">Konto erstellen</h1>
          <p class="mt-1 text-sm text-gray-500">Lege dein Konto für den PaketManager an.</p>

          <form class="mt-6 flex flex-col gap-4" novalidate @submit.prevent="handleRegister">
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
              <label for="email" class="text-sm font-medium text-gray-700">E-Mail</label>
              <input
                id="email"
                v-model="email"
                type="email"
                autocomplete="email"
                required
                class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
            </div>

            <div class="flex flex-col gap-1.5">
              <label for="password" class="text-sm font-medium text-gray-700">Passwort</label>
              <input
                id="password"
                v-model="password"
                type="password"
                autocomplete="new-password"
                required
                :minlength="MIN_PASSWORD_LENGTH"
                class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
              <p class="text-xs text-gray-500">Mindestens {{ MIN_PASSWORD_LENGTH }} Zeichen.</p>
            </div>

            <div class="flex flex-col gap-1.5">
              <label for="verify-password" class="text-sm font-medium text-gray-700">
                Passwort bestätigen
              </label>
              <input
                id="verify-password"
                v-model="verifyPassword"
                type="password"
                autocomplete="new-password"
                required
                class="w-full rounded-lg border border-gray-300 px-3 py-2.5 text-sm text-gray-900 focus:border-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-600"
              />
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
              {{ loading ? 'Konto wird erstellt …' : 'Konto erstellen' }}
            </button>
          </form>
        </div>

        <div class="border-t border-gray-200 px-6 py-4 md:px-8 text-sm text-gray-500">
          Schon ein Konto?
          <RouterLink to="/login" class="font-medium text-blue-600 hover:text-blue-700">
            Einloggen
          </RouterLink>
        </div>
      </div>
    </main>
  </div>
</template>