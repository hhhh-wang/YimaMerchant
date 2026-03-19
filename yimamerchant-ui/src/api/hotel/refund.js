import request from '@/utils/request'

// 退款分页列表
export function listRefund(query) {
  return request({
    url: '/hotel/refund/list',
    method: 'get',
    params: query
  })
}

// 退款详情
export function getRefundDetail(id) {
  return request({
    url: '/hotel/refund/' + id,
    method: 'get'
  })
}

// 同意退款（商家审核）
export function approveRefund(data) {
  return request({
    url: '/hotel/refund/approve',
    method: 'post',
    data: data
  })
}

// 拒绝退款（商家审核）
export function rejectRefund(data) {
  return request({
    url: '/hotel/refund/reject',
    method: 'post',
    data: data
  })
}

