import axios from 'axios'
import store from '@/store'
import { getToken } from '@/utils/auth'
import { MessageBox, Message } from 'element-ui'

const service = axios.create({
	baseURL: 'http://localhost:8080/api',
	timeout: 5000
})

// request interceptor
service.interceptors.request.use(
  request => {
    if (store.getters.token) {
      request.headers.Authorization = getToken()
    }
    return request
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    if (response.status !== 200) {
      Message({
        message: response.message || 'Error',
        type: 'error',
        duration: 5 * 1000
      })
      // 497: Illegal token; 498: Other clients logged in; 499: Token expired;
      if (response.status === 497 || response.status === 498 || response.status === 499) {
        MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
          confirmButtonText: 'Re-Login',
          cancelButtonText: 'Cancel',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload() // refresh page
          })
        })
      }
      return Promise.reject(new Error(response.message || 'Error'))
    } else {
      return response
    }
  },
  error => {
    console.log(error)
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
