import { getInfo } from '@/api/user'
import { addRouter } from '@/router'

const state = {
  name: '',
  avatar: ''
}

const mutations = {
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response.data
        const { name, avatar, routes } = data
        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        addRouter(routes)
        resolve()
      }).catch(() => reject())
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
