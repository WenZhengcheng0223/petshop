import request from '@/utils/request'

// 查询分类列表
export function listCategory(query) {
  return request({
    url: '/goods/category/list',
    method: 'get',
    params: query
  })
}

// 查询分类详细
export function getCategory(catId) {
  return request({
    url: '/goods/category/' + catId,
    method: 'get'
  })
}

// 新增分类
export function addCategory(data) {
  return request({
    url: '/goods/category',
    method: 'post',
    data: data
  })
}

// 修改分类
export function updateCategory(data) {
  return request({
    url: '/goods/category',
    method: 'put',
    data: data
  })
}

// 删除分类
export function delCategory(catId) {
  return request({
    url: '/goods/category/' + catId,
    method: 'delete'
  })
}

// 删除分类
export function deleteImg(catId, url) {
  return request({
    url: '/goods/category/img/' + catId,
    method: 'delete',
    params: {
      url: url
    }
  })
}
