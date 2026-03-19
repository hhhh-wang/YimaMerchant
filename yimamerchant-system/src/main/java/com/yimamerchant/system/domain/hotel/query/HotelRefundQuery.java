package com.yimamerchant.system.domain.hotel.query;

import java.util.Date;

/**
 * 酒店退款分页查询参数
 */
public class HotelRefundQuery
{
    /** 商家编号（服务层注入，不由前端传参） */
    public Long merchantId;

    /** 页码 */
    public Integer pageNum;

    /** 每页条数 */
    public Integer pageSize;

    /** 订单号 */
    public String orderNo;

    /** 退款单号 */
    public String refundNo;

    /** 房型名称 */
    public String roomTypeName;

    /** 退款状态 */
    public String refundStatus;

    /** 申请开始时间 */
    public Date beginApplyTime;

    /** 申请结束时间 */
    public Date endApplyTime;

    /** 入住开始日期 */
    public Date beginCheckInDate;

    /** 入住结束日期 */
    public Date endCheckInDate;

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public Integer getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(Integer pageNum)
    {
        this.pageNum = pageNum;
    }

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public String getOrderNo()
    {
        return orderNo;
    }

    public void setOrderNo(String orderNo)
    {
        this.orderNo = orderNo;
    }

    public String getRefundNo()
    {
        return refundNo;
    }

    public void setRefundNo(String refundNo)
    {
        this.refundNo = refundNo;
    }

    public String getRoomTypeName()
    {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName)
    {
        this.roomTypeName = roomTypeName;
    }

    public String getRefundStatus()
    {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus)
    {
        this.refundStatus = refundStatus;
    }

    public Date getBeginApplyTime()
    {
        return beginApplyTime;
    }

    public void setBeginApplyTime(Date beginApplyTime)
    {
        this.beginApplyTime = beginApplyTime;
    }

    public Date getEndApplyTime()
    {
        return endApplyTime;
    }

    public void setEndApplyTime(Date endApplyTime)
    {
        this.endApplyTime = endApplyTime;
    }

    public Date getBeginCheckInDate()
    {
        return beginCheckInDate;
    }

    public void setBeginCheckInDate(Date beginCheckInDate)
    {
        this.beginCheckInDate = beginCheckInDate;
    }

    public Date getEndCheckInDate()
    {
        return endCheckInDate;
    }

    public void setEndCheckInDate(Date endCheckInDate)
    {
        this.endCheckInDate = endCheckInDate;
    }

}

