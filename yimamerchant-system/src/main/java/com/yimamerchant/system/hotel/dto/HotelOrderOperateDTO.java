package com.yimamerchant.system.hotel.dto;

import java.util.List;

public class HotelOrderOperateDTO
{
    private String orderNo;
    private List<String> orderNos;
    private String operateRemark;
    private String cancelReason;
    private String refundReason;

    public String getOrderNo() { return orderNo; }
    public void setOrderNo(String orderNo) { this.orderNo = orderNo; }
    public List<String> getOrderNos() { return orderNos; }
    public void setOrderNos(List<String> orderNos) { this.orderNos = orderNos; }
    public String getOperateRemark() { return operateRemark; }
    public void setOperateRemark(String operateRemark) { this.operateRemark = operateRemark; }
    public String getCancelReason() { return cancelReason; }
    public void setCancelReason(String cancelReason) { this.cancelReason = cancelReason; }
    public String getRefundReason() { return refundReason; }
    public void setRefundReason(String refundReason) { this.refundReason = refundReason; }
}
