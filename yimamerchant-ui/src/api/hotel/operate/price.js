import request from '@/utils/request'

export function getPriceCalendar(query) { return request({ url: '/hotel/operate/price/calendar', method: 'get', params: query }) }
export function saveDailyPrice(data) { return request({ url: '/hotel/operate/price/daily', method: 'post', data }) }
export function saveBatchPrice(data) { return request({ url: '/hotel/operate/price/batch', method: 'post', data }) }
export function closePrice(data) { return request({ url: '/hotel/operate/price/close', method: 'post', data }) }
export function copyPrice(data) { return request({ url: '/hotel/operate/price/copy', method: 'post', data }) }
export function restorePrice(data) { return request({ url: '/hotel/operate/price/restore', method: 'post', data }) }
export function listPriceHistory(query) { return request({ url: '/hotel/operate/price/history', method: 'get', params: query }) }
