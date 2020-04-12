import Vue from 'vue'
import Vuex from 'vuex'

import user from './modules/user'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user
  },
  getters: {
    token: state => state.user.token,
    name: state => state.user.name,
    avator: state => state.user.avator
  }
})
