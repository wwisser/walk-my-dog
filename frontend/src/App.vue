<template>
  <div id="app">
    <Auth v-if="!this.isLoggedIn" />
    <HelloWorld v-if="this.isLoggedIn" msg="Welcome to Your Vue.js App"/>
    <Dashboard/>
    <!--<router-view  v-if="this.isLoggedIn"/>-->
  </div>
</template>

<script>
import HelloWorld from '@/components/HelloWorld.vue'
import Auth from '@/components/Auth.vue'

export default {
  data() {
     return {
       isLoggedIn: false
    }
  },
  name: 'App',
  components: {
    HelloWorld,
    Auth
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
