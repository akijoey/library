import request from '@/utils/request'

export function register(data) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function getMajor() {
  return request({
    url: '/user/major',
    method: 'get'
  })
}

export function replace(data) {
  return request({
    url: '/user/replace',
    method: 'post',
    data
  })
}

export function report(data) {
  return request({
    url: '/user/report',
    method: 'post',
    data
  })
}

export function passwd(data) {
  return request({
    url: '/user/passwd',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
