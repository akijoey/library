import Vue from 'vue'
import Cookies from 'vue-cookies'

Vue.use(Cookies)

export function getToken() {
	return this.$cookies.get(key)
}

export function setToken() {
	return this.$cookies.set(key, token)
}

export function removeToken() {
	return this.$cookies.remove(key)
}
