package com.yimamerchant.system.hotel.dto;

import java.util.List;

public class HotelRoomTypeCopyDTO
{
    private Long sourceHotelId;
    private Long targetHotelId;
    private List<Long> roomTypeIds;

    public Long getSourceHotelId() { return sourceHotelId; }
    public void setSourceHotelId(Long sourceHotelId) { this.sourceHotelId = sourceHotelId; }
    public Long getTargetHotelId() { return targetHotelId; }
    public void setTargetHotelId(Long targetHotelId) { this.targetHotelId = targetHotelId; }
    public List<Long> getRoomTypeIds() { return roomTypeIds; }
    public void setRoomTypeIds(List<Long> roomTypeIds) { this.roomTypeIds = roomTypeIds; }
}
