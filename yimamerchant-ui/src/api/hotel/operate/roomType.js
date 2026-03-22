import request from '@/utils/request'

export function listRoomType(query) { return request({ url: '/hotel/operate/roomType/list', method: 'get', params: query }) }
export function getRoomType(id) { return request({ url: '/hotel/operate/roomType/' + id, method: 'get' }) }
export function addRoomType(data) { return request({ url: '/hotel/operate/roomType', method: 'post', data }) }
export function updateRoomType(data) { return request({ url: '/hotel/operate/roomType', method: 'put', data }) }
export function delRoomType(ids) { return request({ url: '/hotel/operate/roomType/' + ids, method: 'delete' }) }
export function updateRoomTypeStatus(data) { return request({ url: '/hotel/operate/roomType/status', method: 'put', data }) }
export function updateRoomTypeSort(data) { return request({ url: '/hotel/operate/roomType/sort', method: 'put', data }) }
export function copyRoomType(data) { return request({ url: '/hotel/operate/roomType/copy', method: 'post', data }) }
