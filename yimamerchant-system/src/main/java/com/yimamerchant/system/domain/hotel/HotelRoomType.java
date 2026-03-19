package com.yimamerchant.system.domain.hotel;

import java.math.BigDecimal;

import com.yimamerchant.common.core.domain.BaseEntity;

/**
 * 酒店房型表 hotel_room_type
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    public Long id;

    /** 酒店编号 */
    public Long hotelId;

    /** 商家编号 */
    public Long merchantId;

    /** 房型名称 */
    public String roomTypeName;

    /** 房型编码 */
    public String roomTypeCode;

    /** 房型图片(JSON数组字符串) */
    public String roomImages;

    /** 床型 */
    public String bedType;

    /** 可住人数 */
    public Integer peopleLimit;

    /** 面积说明 */
    public String area;

    /** 楼层描述 */
    public String floorDesc;

    /** 窗型 */
    public String windowType;

    /** 早餐数量 */
    public Integer breakfastCount;

    /** 是否可加床（Y是 N否） */
    public String extraBedFlag;

    /** 房型描述 */
    public String description;

    /** 默认价格 */
    public BigDecimal basePrice;

    /** 划线价 */
    public BigDecimal marketPrice;

    /** 默认库存 */
    public Integer baseStock;

    /** 已售数量 */
    public Integer soldNum;

    /** 配置状态（0停用 1启用） */
    public String configStatus;

    /** 上架状态（0下架 1上架） */
    public String saleStatus;

    /** 是否可预订（Y是 N否） */
    public String bookableFlag;

    /** 排序号 */
    public Integer sortNum;

    /** 删除标记（0存在 2删除） */
    public String delFlag;

    /** 备注 */
    public String remark;

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

    public String getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(String roomImages) {
        this.roomImages = roomImages;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFloorDesc() {
        return floorDesc;
    }

    public void setFloorDesc(String floorDesc) {
        this.floorDesc = floorDesc;
    }

    public String getWindowType() {
        return windowType;
    }

    public void setWindowType(String windowType) {
        this.windowType = windowType;
    }

    public Integer getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(Integer breakfastCount) {
        this.breakfastCount = breakfastCount;
    }

    public String getExtraBedFlag() {
        return extraBedFlag;
    }

    public void setExtraBedFlag(String extraBedFlag) {
        this.extraBedFlag = extraBedFlag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}

