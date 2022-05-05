import request from '@/utils/request'

// 查询会员列表
export function listDetail(query) {
  return request({
    url: '/person/detail/list',
    method: 'get',
    params: query
  })
}

// 查询会员详细
export function getDetail(userId) {
  return request({
    url: '/person/detail/' + userId,
    method: 'get'
  })
}

// 新增会员
export function addDetail(data) {
  return request({
    url: '/person/detail',
    method: 'post',
    data: data
  })
}

// 修改会员
export function updateDetail(data) {
  return request({
    url: '/person/detail',
    method: 'put',
    data: data
  })
}

// 删除会员
export function delDetail(userId) {
  return request({
    url: '/person/detail/' + userId,
    method: 'delete'
  })
}
