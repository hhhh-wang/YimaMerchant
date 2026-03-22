package com.yimamerchant.system.hotel.dto;

import java.util.List;

public class HotelBindTransferDTO
{
    private Long sourceBdUserId;
    private Long targetBdUserId;
    private List<Long> hotelIds;
    private String transferReason;

    public Long getSourceBdUserId() { return sourceBdUserId; }
    public void setSourceBdUserId(Long sourceBdUserId) { this.sourceBdUserId = sourceBdUserId; }
    public Long getTargetBdUserId() { return targetBdUserId; }
    public void setTargetBdUserId(Long targetBdUserId) { this.targetBdUserId = targetBdUserId; }
    public List<Long> getHotelIds() { return hotelIds; }
    public void setHotelIds(List<Long> hotelIds) { this.hotelIds = hotelIds; }
    public String getTransferReason() { return transferReason; }
    public void setTransferReason(String transferReason) { this.transferReason = transferReason; }
}
