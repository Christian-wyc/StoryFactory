import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue')
  },
  {
    path: '/novel',
    name: 'Novel',
    component: () => import('../views/NovelManagement.vue')
  },
  {
    path: '/outline',
    name: 'Outline',
    component: () => import('../views/OutlineEditor.vue')
  },
  {
    path: '/character',
    name: 'Character',
    component: () => import('../views/CharacterSetting.vue')
  },
  {
    path: '/world',
    name: 'World',
    component: () => import('../views/WorldBuilding.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
