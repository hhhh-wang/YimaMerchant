import request from '@/utils/request'

export function listPending(query) { return request({ url: '/hotel/cooperate/pending/list', method: 'get', params: query }) }
export function getPending(id) { return request({ url: '/hotel/cooperate/pending/' + id, method: 'get' }) }
export function addPending(data) { return request({ url: '/hotel/cooperate/pending', method: 'post', data }) }
export function approvePending(data) { return request({ url: '/hotel/cooperate/pending/approve', method: 'put', data }) }
export function rejectPending(data) { return request({ url: '/hotel/cooperate/pending/reject', method: 'put', data }) }
export function delPending(ids) { return request({ url: '/hotel/cooperate/pending/' + ids, method: 'delete' }) }
