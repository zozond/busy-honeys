import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    //라이브러리 테스트
    {
      path: '/',
      name: 'apex',
      component: () => import('../views/Apexchart.vue')
    },
    {
      path: '/stock',
      name: 'stock',
      component: () => import('../views/StockView.vue')
    },
    {
      path: '/account',
      name: 'account',
      component: () => import('../views/AccountView.vue')
    },
    {
      path: '/setting',
      name: 'setting',
      component: () => import('../views/SettingView.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/Registration',
      name: 'registration',
      component: () => import('../views/RegisterUser.vue')
    },
    {
      path: '/info',
      name: 'info',
      component: () => import('../views/UserInfoView.vue')
    },
  ]
});

export default router;
