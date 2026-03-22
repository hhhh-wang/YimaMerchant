package com.yimamerchant.system.hotel.dto;

import java.math.BigDecimal;
import java.util.List;

public class HotelPriceOperateDTO
{
    private Long hotelId;
    private List<Long> roomTypeIds;
    private List<String> bizDates;
    private BigDecimal settlementPrice;
    private BigDecimal salePrice;
    private BigDecimal commissionRate;
    private Integer inventory;
    private String refundRule;
    private String specialTag;
    private String roomStatus;
    private String remark;

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public List<Long> getRoomTypeIds() { return roomTypeIds; }
    public void setRoomTypeIds(List<Long> roomTypeIds) { this.roomTypeIds = roomTypeIds; }
    public List<String> getBizDates() { return bizDates; }
    public void setBizDates(List<String> bizDates) { this.bizDates = bizDates; }
    public BigDecimal getSettlementPrice() { return settlementPrice; }
    public void setSettlementPrice(BigDecimal settlementPrice) { this.settlementPrice = settlementPrice; }
    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }
    public BigDecimal getCommissionRate() { return commissionRate; }
    public void setCommissionRate(BigDecimal commissionRate) { this.commissionRate = commissionRate; }
    public Integer getInventory() { return inventory; }
    public void setInventory(Integer inventory) { this.inventory = inventory; }
    public String getRefundRule() { return refundRule; }
    public void setRefundRule(String refundRule) { this.refundRule = refundRule; }
    public String getSpecialTag() { return specialTag; }
    public void setSpecialTag(String specialTag) { this.specialTag = specialTag; }
    public String getRoomStatus() { return roomStatus; }
    public void setRoomStatus(String roomStatus) { this.roomStatus = roomStatus; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
}
