import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import('../views/home/index.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: "/opinion_screen/",
  routes
})

export default router
