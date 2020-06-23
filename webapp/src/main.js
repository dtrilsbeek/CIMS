import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Routes from './routes'
import VModal from 'vue-js-modal'
import Keycloak from 'keycloak-js'

const initOptions = {
  url: 'http://localhost:8180/auth',
  realm: 'CIMS-users',
  clientId: 'frontend',
  onLoad:'login-required'
};


const keycloak =  Keycloak(initOptions);

keycloak.init({ onLoad: initOptions.onLoad }).then(auth => {
  if(!auth) {
    window.location.reload();
  } else {
    console.log("Authenticated");
  }


  Vue.use(VueRouter);
  Vue.config.productionTip = false;

  Vue.use(VModal, { dynamic: true, dynamicDefaults: { clickToClose: false } })

  const router = new VueRouter({
    routes: Routes,
    mode: 'history'
  });

  new Vue({
    render: h => h(App),
    router: router
  }).$mount('#app')

  Vue.prototype.$token = keycloak.token;

  setInterval(() =>{
    keycloak.updateToken(70).then(refreshed => {
      if (refreshed) {
        Vue.prototype.$token = keycloak.token;
        console.log('Token refreshed');
      }
    }).catch(()=>{
        console.error('Failed to refresh token');
    });


  }, 30000);

  
}).catch(() => {
  console.error("authentication failed");
});