import { login, getInfo, logout } from '@/api/user'
import { addRouter, resetRouter } from '@/router'
import { getToken, setToken, removeToken } from '@/utils/auth'

const state = {
  token: getToken(),
  name: '',
  avatar: ''
}

const mutations = {
  setToken: (state, token) => {
    state.token = token
    setToken(token)
  },
  setName: (state, name) => {
    state.name = name
  },
  setAvatar: (state, avatar) => {
    state.avatar = avatar
  },
  reset: state => {
    removeToken()
    state.token = ''
    state.name = ''
    state.avatar = ''
  }
}

const actions = {
  login({ commit }, data) {
    return new Promise((resolve, reject) => {
      login(data).then(response => {
        const { data } = response
        const { token } = data
        commit('setToken', token)
        resolve(response)
      }).catch(() => reject())
    })
  },
  getInfo({ commit }) {
    return new Promise((resolve, reject) => {
      getInfo().then(response => {
        const { data } = response
        const { name, avatar, routes } = data
        commit('setName', name)
        commit('setAvatar', avatar)
        addRouter(routes)
        resolve()
      }).catch(() => {
        commit('reset')
        reject()
      })
    })
  },
  logout({ commit }) {
    return new Promise(resolve => {
      logout().then(response => {
        resetRouter()
        commit('reset')
        resolve(response)
      })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
