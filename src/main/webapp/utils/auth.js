import cookies from 'vue-cookies'

export function getToken(key) {
	return cookies.get(key)
}

export function setToken(key, token) {
	return cookies.set(key, token)
}

export function removeToken(key) {
	return cookies.remove(key)
}
