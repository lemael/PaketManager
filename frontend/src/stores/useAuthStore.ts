import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'));
  const isLoggedIn = computed(() => !!token.value);

  function setToken(value: string) {
    token.value = value;
    localStorage.setItem('token', value);
  }

  function logout() {
    token.value = null;
    localStorage.removeItem('token');
  }
  function login() {
    // Simulate a login by setting a dummy token
    setToken('dummy-token');
  }

  return { token, isLoggedIn, setToken, logout, login };
});