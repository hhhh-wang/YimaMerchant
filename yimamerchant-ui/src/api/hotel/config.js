import request from '@/utils/request'

export function getHotelConfig() { return request({ url: '/hotel/config/get', method: 'get' }) }
export function saveHotelConfig(data) { return request({ url: '/hotel/config/save', method: 'put', data }) }
export function listHotelConfigLogs() { return request({ url: '/hotel/config/logs', method: 'get' }) }
