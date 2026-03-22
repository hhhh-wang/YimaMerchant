package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class HotelPriceCalendarItem
{
    private Long id;
    private Long hotelId;
    private Long roomTypeId;
    private String roomTypeName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date bizDate;
    private Integer weekNo;
    private BigDecimal settlementPrice;
    private BigDecimal salePrice;
    private BigDecimal commissionRate;
    private BigDecimal commissionAmount;
    private Integer inventory;
    private String roomStatus;
    private String refundRule;
    private String specialTag;
    private String isDefaultData;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public Long getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }
    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
    public Date getBizDate() { return bizDate; }
    public void setBizDate(Date bizDate) { this.bizDate = bizDate; }
    public Integer getWeekNo() { return weekNo; }
    public void setWeekNo(Integer weekNo) { this.weekNo = weekNo; }
    public BigDecimal getSettlementPrice() { return settlementPrice; }
    public void setSettlementPrice(BigDecimal settlementPrice) { this.settlementPrice = settlementPrice; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }
    public BigDecimal getCommissionAmount() { return commissionAmount; }
    public void setCommissionAmount(BigDecimal commissionAmount) { this.commissionAmount = commissionAmount; }
    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }
    public String getRoomStatus() { return roomStatus; }
    public void setRoomStatus(String roomStatus) { this.roomStatus = roomStatus; }
    public String getRefundRule() { return refundRule; }
    public void setRefundRule(String refundRule) { this.refundRule = refundRule; }
    public String getSpecialTag() { return specialTag; }
    public void setSpecialTag(String specialTag) { this.specialTag = specialTag; }
    public String getIsDefaultData() { return isDefaultData; }
    public void setIsDefaultData(String isDefaultData) { this.isDefaultData = isDefaultData; }
}
