package com.yimamerchant.system.hotel.dto;

import java.util.List;

public class HotelBindBatchDTO
{
    private Long bdUserId;
    private List<Long> hotelIds;
    private String remark;

    public Long getBdUserId() { return bdUserId; }
    public void setBdUserId(Long bdUserId) { this.bdUserId = bdUserId; }
    public List<Long> getHotelIds() { return hotelIds; }
    public void setHotelIds(List<Long> hotelIds) { this.hotelIds = hotelIds; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
