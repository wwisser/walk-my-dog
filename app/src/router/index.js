
import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Auth from '../views/Auth.vue'
import Dashboard from '../views/Dashboard.vue'
import Partners from '../views/Partners.vue'
import Walk from '../views/Walk.vue'
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/auth',
      name: 'Auth',
      component: Auth,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashbard,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/partners',
      name: 'Partners',
      component: Partners,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/walk',
      name: 'Walk',
      component: Walk,
      meta: {
        requireAuth: true
      }
    }
  ]
})