import { getInfo } from '@/api/user'
import { addRouter } from '@/router'

const state = {
  name: '',
  avatar: ''
}

const mutations = {
  setter: (state, name, avatar) => {
    state.name = name
    state.avatar = avatar
  },
  setName: (state, name) => {
    state.name = name
  },
  setAvatar: (state, avatar) => {
    state.avatar = avatar
  },
  reset: state => {
    state.name = ''
    state.avatar = ''
  }
}

const actions = {
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response.data
        const { name, avatar, routes } = data
        commit('setter', name, avatar)
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
