package com.yimamerchant.system.domain.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 房型列表 VO
 *
 * 对应接口文档 5.1 列表行字段。
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeVO
{
    public Long id;
    public Long hotelId;
    public Long merchantId;
    public String roomTypeName;
    public String roomTypeCode;
    public List<String> roomImages;
    /** 首图，通常取 roomImages 第一张 */
    public String firstImage;
    public String bedType;
    public String bedTypeLabel;
    public Integer peopleLimit;
    public BigDecimal basePrice;
    public BigDecimal marketPrice;
    public Integer baseStock;
    public Integer soldNum;
    public Integer availableNum;
    public String configStatus;
    public String saleStatus;
    public String bookableFlag;
    public Integer sortNum;
    public Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public List<String> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(List<String> roomImages) {
        this.roomImages = roomImages;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public String getBedTypeLabel() {
        return bedTypeLabel;
    }

    public void setBedTypeLabel(String bedTypeLabel) {
        this.bedTypeLabel = bedTypeLabel;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getBaseStock() {
        return baseStock;
    }

    public void setBaseStock(Integer baseStock) {
        this.baseStock = baseStock;
    }

    public Integer getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum) {
        this.soldNum = soldNum;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public String getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(String configStatus) {
        this.configStatus = configStatus;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getBookableFlag() {
        return bookableFlag;
    }

    public void setBookableFlag(String bookableFlag) {
        this.bookableFlag = bookableFlag;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

