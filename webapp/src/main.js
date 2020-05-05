import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from './routes'

window.axios = require('axios');

//const axios = require('axios');

Vue.use(VueRouter);
Vue.config.productionTip = false;

const router = new VueRouter({
  routes: Routes,
  mode: 'history'
});

new Vue({
  render: h => h(App),
  router: router
}).$mount('#app')
