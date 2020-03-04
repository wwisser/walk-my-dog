import Vue from 'vue'
import VueRouter from 'vue-router'


import App from './App.vue'
import router from './router'


//import './assets/css/styles.css'
//import './assets/css/bootstrap.css'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.config.productionTip = false
// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

//Routes
Vue.use(VueRouter)





Vue.config.productionTip = false

new Vue({

  router: router,
  render: h => h(App),
}).$mount('#app')
