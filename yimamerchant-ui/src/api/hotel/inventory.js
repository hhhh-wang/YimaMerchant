import request from '@/utils/request'

// 库存价格分页列表
export function listInventory(query) {
  return request({
    url: '/hotel/inventory/list',
    method: 'get',
    params: query
  })
}

// 修改单个房型默认价格
export function updateBasePrice(data) {
  return request({
    url: '/hotel/inventory/updateBasePrice',
    method: 'put',
    data: data
  })
}

// 修改单个房型默认库存
export function updateBaseStock(data) {
  return request({
    url: '/hotel/inventory/updateBaseStock',
    method: 'put',
    data: data
  })
}

// 批量修改价格库存
export function batchUpdateInventory(data) {
  return request({
    url: '/hotel/inventory/batchUpdate',
    method: 'put',
    data: data
  })
}

// 修改可预订状态
export function changeBookableFlag(data) {
  return request({
    url: '/hotel/inventory/changeBookableFlag',
    method: 'put',
    data: data
  })
}

// 修改上下架状态
export function changeSaleStatus(data) {
  return request({
    url: '/hotel/inventory/changeSaleStatus',
    method: 'put',
    data: data
  })
}

