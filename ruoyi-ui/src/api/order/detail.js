import request from '@/utils/request'

// 查询订单列表
export function listDetail(query) {
  return request({
    url: '/order/detail/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getDetail(orderId) {
  return request({
    url: '/order/detail/' + orderId,
    method: 'get'
  })
}

// 新增订单
export function addDetail(data) {
  return request({
    url: '/order/detail',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateDetail(data) {
  return request({
    url: '/order/detail',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delDetail(orderId) {
  return request({
    url: '/order/detail/' + orderId,
    method: 'delete'
  })
}
