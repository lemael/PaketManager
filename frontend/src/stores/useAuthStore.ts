import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const token = ref<string | null>(localStorage.getItem('token'));
  const username = ref<string | null>(localStorage.getItem('username'));
  const isLoggedIn = computed(() => !!token.value);

  function setToken(value: string) {
    token.value = value;
    localStorage.setItem('token', value);
  }

  function setUsername(value: string) {
    username.value = value;
    localStorage.setItem('username', value);
  }

  function logout() {
    token.value = null;
    username.value = null;
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }
  function login() {
    // Simulate a login by setting a dummy token
    setToken('dummy-token');
  }

  return { token, username, isLoggedIn, setToken, setUsername, logout, login };
});