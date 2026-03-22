import request from '@/utils/request'

export function listHotelOrder(query) { return request({ url: '/hotel/service/order/list', method: 'get', params: query }) }
export function getHotelOrder(orderNo) { return request({ url: '/hotel/service/order/' + orderNo, method: 'get' }) }
export function confirmHotelOrder(data) { return request({ url: '/hotel/service/order/confirm', method: 'put', data }) }
export function cancelHotelOrder(data) { return request({ url: '/hotel/service/order/cancel', method: 'put', data }) }
export function checkinHotelOrder(data) { return request({ url: '/hotel/service/order/checkin', method: 'put', data }) }
export function checkoutHotelOrder(data) { return request({ url: '/hotel/service/order/checkout', method: 'put', data }) }
export function remarkHotelOrder(data) { return request({ url: '/hotel/service/order/remark', method: 'post', data }) }
export function listHotelOrderLogs(orderNo) { return request({ url: '/hotel/service/order/logs', method: 'get', params: { orderNo } }) }
export function refundHotelOrder(data) { return request({ url: '/hotel/service/order/refund', method: 'post', data }) }
export function disputeHotelOrder(data) { return request({ url: '/hotel/service/order/dispute', method: 'post', data }) }
