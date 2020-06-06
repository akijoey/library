import request from '@/utils/request'

export function getSide() {
  return request({
    url: '/category/side',
    method: 'get'
  })
}
