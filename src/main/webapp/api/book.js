import request from '@/utils/request'

export function getList(params) {
	return request({
		url: '/book/list/' + params,
		method: 'get'
	})
}

export function getDetail(data) {
  return request({
    url: '/book/detail',
    method: 'post',
    data
  })
}
