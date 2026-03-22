package com.yimamerchant.system.hotel.dto;

public class HotelPartnerStatusDTO
{
    private Long hotelId;
    private String cooperateStatus;
    private String operateReason;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getCooperateStatus() { return cooperateStatus; }
    public void setCooperateStatus(String cooperateStatus) { this.cooperateStatus = cooperateStatus; }
    public String getOperateReason() { return operateReason; }
    public void setOperateReason(String operateReason) { this.operateReason = operateReason; }
}
