import request from '@/utils/request'

export function listHotelBill(query) { return request({ url: '/hotel/finance/bill/list', method: 'get', params: query }) }
export function generateHotelBill(data) { return request({ url: '/hotel/finance/bill/generate', method: 'post', data }) }
export function getHotelBill(billNo) { return request({ url: '/hotel/finance/bill/' + billNo, method: 'get' }) }
export function listHotelBillOrders(query) { return request({ url: '/hotel/finance/bill/orders', method: 'get', params: query }) }
export function confirmHotelBill(data) { return request({ url: '/hotel/finance/bill/confirm', method: 'put', data }) }
export function disputeHotelBill(data) { return request({ url: '/hotel/finance/bill/dispute', method: 'put', data }) }
export function recalculateHotelBill(data) { return request({ url: '/hotel/finance/bill/recalculate', method: 'put', data }) }
export function payHotelBill(data) { return request({ url: '/hotel/finance/bill/payment', method: 'post', data }) }
export function getHotelBillStatistics(query) { return request({ url: '/hotel/finance/bill/statistics', method: 'get', params: query }) }
