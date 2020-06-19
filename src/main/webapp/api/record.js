import request from '@/utils/request'

export function getCount(uid) {
  return request({
    url: `/record/count/${uid}`,
		method: 'get'
  })
}

export function getList(page, size, uid) {
	return request({
    url: `/record/list/${page}/${size}/${uid}`,
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

export function deleting(data) {
  return request({
    url: '/record/delete',
    method: 'post',
    data
  })
}
