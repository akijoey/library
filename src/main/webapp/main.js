import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'

import '@/components' // global components
import '@/styles/index.scss'  // global styles
import '@/permission' // global before guards

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
