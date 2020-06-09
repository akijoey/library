import request from '@/utils/request'

export function getTotal() {
  return request({
    url: '/record/total',
		method: 'get'
  })
}

export function getTable(page, size) {
	return request({
    url: `/record/table/${page}/${size}`,
    method: 'get'
	})
}

export function borrowing(data) {
  return request({
    url: '/record/borrow',
    method: 'post',
    data
  })
}

export function returning(data) {
  return request({
    url: '/record/return',
    method: 'post',
    data
  })
}

export function renewing(data) {
  return request({
    url: '/record/renew',
    method: 'post',
    data
  })
}
