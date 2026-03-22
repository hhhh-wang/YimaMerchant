import request from '@/utils/request'

export function listPartner(query) { return request({ url: '/hotel/cooperate/partner/list', method: 'get', params: query }) }
export function getPartner(hotelId) { return request({ url: '/hotel/cooperate/partner/' + hotelId, method: 'get' }) }
export function updatePartner(data) { return request({ url: '/hotel/cooperate/partner', method: 'put', data }) }
export function updatePartnerStatus(data) { return request({ url: '/hotel/cooperate/partner/status', method: 'put', data }) }
export function getPartnerStatistics(query) { return request({ url: '/hotel/cooperate/partner/statistics', method: 'get', params: query }) }
export function resetPartnerPassword(hotelId) { return request({ url: '/hotel/cooperate/partner/resetPassword', method: 'put', params: { hotelId } }) }
export function listPartnerAccounts(hotelId) { return request({ url: '/hotel/cooperate/partner/account/list', method: 'get', params: { hotelId } }) }
