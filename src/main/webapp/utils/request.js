import axios from 'axios'
import store from '@/store'
import { Message as $message } from 'element-ui'

const service = axios.create({
	baseURL: 'http://193.112.128.94:7001/api',
	timeout: 5000
})

// request interceptor
service.interceptors.request.use(request => {
  const token = store.getters.token
  if (token !== null) {
    request.headers.authorization = 'Bearer ' + token
  }
  return request
}, error => {
  console.error(error)
  return Promise.reject(error)
})

// response interceptor
service.interceptors.response.use(response => {
  const { status, message } = response.data
  if (status !== 200) {
    $message.error(message)
    const statuses = [
      495,  // 495: Token Not Found;
      496,  // 496: Username Not Found;
      497,  // 497: Illegal Token;
      498,  // 498: Account Expired;
      499   // 499: Token Expired;
    ]
    if (statuses.indexOf(status) !== -1) {
      store.dispatch('user/logout').then(() => {
        setTimeout(() => {
          location.reload() // refresh page
        }, 2000)
      })
    }
    const error = new Error(message)
    return Promise.reject(error)
  } else {
    return response.data
  }
}, error => {
  console.error(error)
  return Promise.reject(error)
})

export default service
