import request from '@/utils/request'

export function listBind(query) { return request({ url: '/hotel/cooperate/bind/list', method: 'get', params: query }) }
export function batchBind(data) { return request({ url: '/hotel/cooperate/bind/batch', method: 'post', data }) }
export function batchUnbind(ids) { return request({ url: '/hotel/cooperate/bind/' + ids, method: 'delete' }) }
export function transferBind(data) { return request({ url: '/hotel/cooperate/bind/transfer', method: 'put', data }) }
export function listBindHistory(query) { return request({ url: '/hotel/cooperate/bind/history', method: 'get', params: query }) }
export function listBdOptions(keyword) { return request({ url: '/hotel/cooperate/bind/bdOptions', method: 'get', params: { keyword } }) }
export function listHotelOptions(keyword) { return request({ url: '/hotel/cooperate/bind/hotelOptions', method: 'get', params: { keyword } }) }
