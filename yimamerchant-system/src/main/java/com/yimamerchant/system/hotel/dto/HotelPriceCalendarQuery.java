package com.yimamerchant.system.hotel.dto;

public class HotelPriceCalendarQuery
{
    private Long hotelId;
    private String roomTypeIds;
    private String month;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getRoomTypeIds() { return roomTypeIds; }
    public void setRoomTypeIds(String roomTypeIds) { this.roomTypeIds = roomTypeIds; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
}
