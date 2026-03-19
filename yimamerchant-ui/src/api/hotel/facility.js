import request from '@/utils/request'

// 查询设施列表
export function listFacility(query) {
  return request({
    url: '/hotel/facility/list',
    method: 'get',
    params: query
  })
}

// 查询房型已绑定设施（返回 ID 列表）
export function listRoomTypeFacilities(roomTypeId) {
  return request({
    url: '/hotel/facility/roomType/' + roomTypeId,
    method: 'get'
  })
}

// 保存房型设施绑定
export function saveRoomTypeFacilities(data) {
  return request({
    url: '/hotel/facility/saveRoomTypeFacilities',
    method: 'post',
    data: data
  })
}

