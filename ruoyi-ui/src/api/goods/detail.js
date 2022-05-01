import request from '@/utils/request'

// 查询商品列表
export function listDetail(query) {
  return request({
    url: '/goods/detail/list',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getDetail(goodsId) {
  return request({
    url: '/goods/detail/' + goodsId,
    method: 'get'
  })
}

// 新增商品
export function addDetail(data) {
  return request({
    url: '/goods/detail',
    method: 'post',
    data: data
  })
}

// 修改商品
export function updateDetail(data) {
  return request({
    url: '/goods/detail',
    method: 'put',
    data: data
  })
}

// 删除商品
export function delDetail(goodsId) {
  return request({
    url: '/goods/detail/' + goodsId,
    method: 'delete'
  })
}


export function removeImg(ossId) {
  return request({
    url: '/goods/oss/' + ossId,
    method: 'delete'
  })
}
