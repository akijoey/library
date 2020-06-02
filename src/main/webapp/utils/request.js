import axios from 'axios'
import { getToken, removeToken } from '@/utils/auth'
import { MessageBox, Message } from 'element-ui'

const service = axios.create({
	baseURL: 'http://localhost:8080/api',
	timeout: 5000
})

// request interceptor
service.interceptors.request.use(request => {
  const token = getToken()
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
    Message.error(message)
    // 495: Token Not Found; 496: Username Not Found; 497: Illegal Token; 498: Account Expired; 499: Token Expired;
    if (status === 495 || status === 496 || status === 497 || status === 498 || status === 499) {
      MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
        confirmButtonText: 'Re-Login',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        removeToken()
        location.reload() // refresh page
      })
    }
    const error = new Error(message)
    return Promise.reject(error)
  } else {
    return response
  }
}, error => {
  console.error(error)
  return Promise.reject(error)
})

export default service
