import request from '@/utils/request'

// 房型列表
export function listRoomType(query) {
  return request({
    url: '/hotel/roomType/list',
    method: 'get',
    params: query
  })
}

// 查询房型详情
export function getRoomType(id) {
  return request({
    url: '/hotel/roomType/' + id,
    method: 'get'
  })
}

// 新增房型
export function addRoomType(data) {
  return request({
    url: '/hotel/roomType',
    method: 'post',
    data: data
  })
}

// 修改房型
export function updateRoomType(data) {
  return request({
    url: '/hotel/roomType',
    method: 'put',
    data: data
  })
}

// 删除房型
export function delRoomType(id) {
  return request({
    url: '/hotel/roomType/' + id,
    method: 'delete'
  })
}

// 修改配置状态
export function changeRoomTypeConfigStatus(data) {
  return request({
    url: '/hotel/roomType/changeConfigStatus',
    method: 'put',
    data: data
  })
}

// 修改上架状态
export function changeRoomTypeSaleStatus(data) {
  return request({
    url: '/hotel/roomType/changeSaleStatus',
    method: 'put',
    data: data
  })
}

// 房型完整性校验
export function checkRoomTypeComplete(id) {
  return request({
    url: '/hotel/roomType/checkComplete/' + id,
    method: 'get'
  })
}

