package com.yimamerchant.system.domain.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款详情 VO
 */
public class HotelRefundDetailVO
{
    public Long id;

    public String refundNo;

    public Long orderId;

    public String orderNo;

    public Long roomTypeId;

    public String roomTypeName;

    public String guestName;

    public String guestPhone;

    /** 入住日期（yyyy-MM-dd） */
    public String checkInDate;

    /** 离店日期（yyyy-MM-dd） */
    public String checkOutDate;

    public BigDecimal orderAmount;

    public BigDecimal applyRefundAmount;

    public String refundReason;

    public String refundStatus;

    public String auditRemark;

    public String auditBy;

    /** 取消规则说明（一期：从酒店当前规则补充） */
    public String cancelRule;

    /** 申请时间 */
    public Date createTime;

    public Date getAuditTime()
    {
        return auditTime;
    }

    /** 处理时间 */
    public Date auditTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRefundNo()
    {
        return refundNo;
    }

    public void setRefundNo(String refundNo)
    {
        this.refundNo = refundNo;
    }

    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public Long getRoomTypeId()
    {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId)
    {
        this.roomTypeId = roomTypeId;
    }

    public String getRoomTypeName()
    {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName)
    {
        this.roomTypeName = roomTypeName;
    }

    public String getGuestName()
    {
        return guestName;
    }

    public void setGuestName(String guestName)
    {
        this.guestName = guestName;
    }

    public String getGuestPhone()
    {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone)
    {
        this.guestPhone = guestPhone;
    }

    public String getCheckInDate()
    {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate()
    {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    public BigDecimal getOrderAmount()
    {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount)
    {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getApplyRefundAmount()
    {
        return applyRefundAmount;
    }

    public void setApplyRefundAmount(BigDecimal applyRefundAmount)
    {
        this.applyRefundAmount = applyRefundAmount;
    }

    public String getRefundReason()
    {
        return refundReason;
    }

    public void setRefundReason(String refundReason)
    {
        this.refundReason = refundReason;
    }

    public String getRefundStatus()
    {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }

    public String getAuditRemark()
    {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark)
    {
        this.auditRemark = auditRemark;
    }

    public String getAuditBy()
    {
        return auditBy;
    }

    public void setAuditBy(String auditBy)
    {
        this.auditBy = auditBy;
    }

    public String getCancelRule()
    {
        return cancelRule;
    }

    public void setCancelRule(String cancelRule)
    {
        this.cancelRule = cancelRule;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }
}

