import request from '@/utils/request'

// 查询当前商家酒店信息
export function getHotelInfo() {
  return request({
    url: '/hotel/info/getInfo',
    method: 'get'
  })
}

// 新增或保存酒店信息（有则更新，无则新增）
export function saveHotelInfo(data) {
  return request({
    url: '/hotel/info/save',
    method: 'post',
    data: data
  })
}

// 修改酒店状态
export function changeHotelStatus(data) {
  return request({
    url: '/hotel/info/changeStatus',
    method: 'put',
    data: data
  })
}

