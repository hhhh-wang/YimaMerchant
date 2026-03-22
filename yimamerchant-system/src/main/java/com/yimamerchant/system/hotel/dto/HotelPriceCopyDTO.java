package com.yimamerchant.system.hotel.dto;

import java.util.List;

public class HotelPriceCopyDTO
{
    private Long hotelId;
    private List<Long> roomTypeIds;
    private String sourceDate;
    private List<String> targetDates;
    private String remark;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public List<Long> getRoomTypeIds() { return roomTypeIds; }
    public void setRoomTypeIds(List<Long> roomTypeIds) { this.roomTypeIds = roomTypeIds; }
    public String getSourceDate() { return sourceDate; }
    public void setSourceDate(String sourceDate) { this.sourceDate = sourceDate; }
    public List<String> getTargetDates() { return targetDates; }
    public void setTargetDates(List<String> targetDates) { this.targetDates = targetDates; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
