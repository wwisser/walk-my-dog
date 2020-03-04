
import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Dashboard from '../views/Dashboard.vue'
import Partners from '../views/Partners.vue'
import Walk from '../views/Walk.vue'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
    },
    {
      path: '/partners',
      name: 'Partners',
      component: Partners,
    },
    {
      path: '/walk',
      name: 'Walk',
      component: Walk,
    }
  ]
})