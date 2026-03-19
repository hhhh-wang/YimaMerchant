package com.yimamerchant.system.domain.hotel;

import java.math.BigDecimal;
import java.util.Date;

import com.yimamerchant.common.core.domain.BaseEntity;

/**
 * 酒店退款单表 hotel_refund_order
 *
 * 注意：按你的要求该类不显式生成 getter/setter，由 IDEA 后续按需生成。
 */
public class HotelRefundOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    public Long id;

    /** 退款单号 */
    public String refundNo;

    /** 订单编号 */
    public Long orderId;

    /** 订单号 */
    public String orderNo;

    /** 酒店编号 */
    public Long hotelId;

    /** 商家编号 */
    public Long merchantId;

    /** 房型编号 */
    public Long roomTypeId;

    /** 入住人 */
    public String guestName;

    /** 联系电话 */
    public String guestPhone;

    /** 入住日期 */
    public Date checkInDate;

    /** 离店日期 */
    public Date checkOutDate;

    /** 订单金额 */
    public BigDecimal orderAmount;

    /** 申请退款金额 */
    public BigDecimal applyRefundAmount;

    /** 退款原因 */
    public String refundReason;

    /** 退款状态 */
    public String refundStatus;

    /** 审核备注 */
    public String auditRemark;

    /** 审核人 */
    public String auditBy;

    /** 审核时间 */
    public Date auditTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getApplyRefundAmount() {
        return applyRefundAmount;
    }

    public void setApplyRefundAmount(BigDecimal applyRefundAmount) {
        this.applyRefundAmount = applyRefundAmount;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getAuditBy() {
        return auditBy;
    }

    public void setAuditBy(String auditBy) {
        this.auditBy = auditBy;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}

