import { useAuthStore } from '@/stores/useAuthStore';
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';

// Lazy loading des vues pour optimiser les performances
const DashboardView = () => import('@/views/dashboard/DashboardView.vue');
const ColisView = () => import('@/views/dashboard/ColisView.vue');
const TransportsView = () => import('@/views/dashboard/TransportsView.vue');
const FahrerView = () => import('@/views/dashboard/FahrerView.vue');
const AutoView = () => import('@/views/dashboard/AutoView.vue');
const KundenView = () => import('@/views/dashboard/KundenView.vue');
const NotFoundView = () => import('@/views/NotFoundView.vue');
const LoginView = () => import('@/views/auth/LoginView.vue');
const RegisterView = () => import('@/views/auth/RegisterView.vue');


const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/dashboard',
    meta: {
      hideLayout: true,
    },
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardView,
    meta: {
      title: 'Dashboard - PaketManager',
      requiresAuth: false,
    },
  },
  {
    path: '/colis',
    name: 'Colis',
    component: ColisView,
    meta: {
      title: 'Gestion des Colis - PaketManager',
      requiresAuth: true,
      hideLayout: false,
    },
  },
  {
    path: '/transports',
    name: 'Transports',
    component: TransportsView,
    meta: {
      title: 'Transports - PaketManager',
      requiresAuth: true,
      hideLayout: false,
    },
  },
  {
    path: '/fahrer',
    name: 'Fahrer',
    component: FahrerView,
    meta: {
      title: 'Chauffeurs (Fahrer) - PaketManager',
      requiresAuth: true,
      hideLayout: false,
    },
  },
  {
    path: '/autos',
    name: 'Autos',
    component: AutoView,
    meta: {
      title: 'Véhicules (Autos) - PaketManager',
      requiresAuth: true,
      hideLayout: false,
    },
  },
  {
    path: '/kunden',
    name: 'Kunden',
    component: KundenView,
    meta: {
      title: 'Kunden - PaketManager',
      requiresAuth: true,
    },
  },
  {
    // Capture les routes inconnues
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: NotFoundView,
    meta: {
      title: 'Page non trouvée - PaketManager',
      hideLayout: true,
    },
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: {
      title: 'Einloggen - PaketManager',
      hideLayout: true,
    },
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
    meta: {
      title: 'Konto erstellen - PaketManager',
      hideLayout: true,
    },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(_to, _from, savedPosition) {
    if (savedPosition) {
      return savedPosition;
    }
    return { top: 0 };
  },
});

// Guard de navigation : Met à jour le document.title automatiquement
router.beforeEach((to) => {
  if (to.meta.title) {
    document.title = to.meta.title;
  }

  const authStore = useAuthStore();

  if (to.meta.requiresAuth && !authStore.isLoggedIn) {
    return { path: '/login', query: { redirect: to.fullPath } };
  }

  if (authStore.isLoggedIn && (to.path === '/login' || to.path === '/register')) {
    return '/dashboard';
  }
});
export default router;