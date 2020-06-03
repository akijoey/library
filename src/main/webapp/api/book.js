import request from '@/utils/request'

export function getTotal(cid) {
  return request({
    url: '/book/total/' + cid,
		method: 'get'
  })
}

export function getList(page, size, cid) {
	return request({
		url: `/book/list/${page}/${size}/${cid}`,
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
