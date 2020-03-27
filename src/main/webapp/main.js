import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

var axios = require('axios')
axios.defaults.baseURL = 'http://localhost:8080/api'

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
