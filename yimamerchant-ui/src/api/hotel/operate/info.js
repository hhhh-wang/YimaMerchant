import request from '@/utils/request'

export function listHotelInfo(query) { return request({ url: '/hotel/operate/info/list', method: 'get', params: query }) }
export function getHotelInfo(hotelId) { return request({ url: '/hotel/operate/info/' + hotelId, method: 'get' }) }
export function addHotelInfo(data) { return request({ url: '/hotel/operate/info', method: 'post', data }) }
export function updateHotelInfo(data) { return request({ url: '/hotel/operate/info', method: 'put', data }) }
export function updateHotelInfoStatus(data) { return request({ url: '/hotel/operate/info/status', method: 'put', data }) }
export function listHotelInfoOptions(keyword) { return request({ url: '/hotel/operate/info/options', method: 'get', params: { keyword } }) }
export function listHotelChangeLog(hotelId) { return request({ url: '/hotel/operate/info/changeLog', method: 'get', params: { hotelId } }) }
