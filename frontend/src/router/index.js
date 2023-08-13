import { createRouter, createWebHistory } from 'vue-router'
import TraidingView from '../views/TraidingView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    //라이브러리 테스트
    {
      path: '/',
      name: 'home',
      component: TraidingView
    },
    {
      path: '/D3chart',
      name: 'D3chart',
      component: () => import('../views/D3chart.vue')
    },
    {
      path: '/apex',
      name: 'apex',
      component: () => import('../views/Apexchart.vue')
    },

    //여기서부터가 진짜
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
  ]
})

export default router
