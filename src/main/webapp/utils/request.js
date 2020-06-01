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
      request.headers.Authorization = 'Bearer ' + getToken()
    }
    return request
  }, error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    const { status, message } = response.data
    if (status !== 200) {
      Message.error(message || 'Error')
      // 495: Token Not Found; 496: Username Not Found; 497: Illegal Token; 498: Account Expired; 499: Token Expired;
      if (status === 401 || status === 495 || status === 496 || status === 497 || status === 498 || status === 499) {
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
      return Promise.reject(new Error(message || 'Error'))
    } else {
      return response
    }
  }, error => {
    console.log(error)
    Message.error(error.message)
    return Promise.reject(error)
  }
)

export default service
