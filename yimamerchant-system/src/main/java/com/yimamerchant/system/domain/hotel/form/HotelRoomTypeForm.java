package com.yimamerchant.system.domain.hotel.form;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/**
 * 房型新增 / 修改表单
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeForm
{
    /** 主键，修改时必填 */
    public Long id;

    @NotBlank(message = "房型名称不能为空")
    @Size(max = 50, message = "房型名称最多50字")
    public String roomTypeName;

    @NotBlank(message = "房型编码不能为空")
    @Size(max = 50, message = "房型编码最多50字")
    public String roomTypeCode;

    /** 房型图片地址数组 */
    @NotNull(message = "房型图片不能为空")
    public List<String> roomImages;

    @NotBlank(message = "床型不能为空")
    public String bedType;

    @NotNull(message = "可住人数不能为空")
    @PositiveOrZero(message = "可住人数不能小于0")
    public Integer peopleLimit;

    /** 面积说明 */
    public String area;

    /** 楼层描述 */
    public String floorDesc;

    /** 窗型 */
    public String windowType;

    /** 早餐数 */
    @PositiveOrZero(message = "早餐数不能小于0")
    public Integer breakfastCount;

    /** 是否可加床（Y/N） */
    public String extraBedFlag;

    /** 房型描述 */
    public String description;

    /** 默认价格 */
    @PositiveOrZero(message = "默认价格不能小于0")
    public BigDecimal basePrice;

    /** 划线价 */
    @PositiveOrZero(message = "划线价不能小于0")
    public BigDecimal marketPrice;

    /** 默认库存 */
    @PositiveOrZero(message = "默认库存不能小于0")
    public Integer baseStock;

    /** 配置状态（0停用 1启用） */
    @NotBlank(message = "配置状态不能为空")
    public String configStatus;

    /** 上架状态（0下架 1上架） */
    public String saleStatus;

    /** 是否可预订（Y/N） */
    @NotBlank(message = "是否可预订不能为空")
    public String bookableFlag;

    /** 排序号 */
    @PositiveOrZero(message = "排序号不能小于0")
    public Integer sortNum;

    /** 备注 */
    public String remark;

    /** 已绑定设施 ID 列表 */
    public List<Long> facilityIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "房型名称不能为空") @Size(max = 50, message = "房型名称最多50字") String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(@NotBlank(message = "房型名称不能为空") @Size(max = 50, message = "房型名称最多50字") String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

    public @NotBlank(message = "房型编码不能为空") @Size(max = 50, message = "房型编码最多50字") String getRoomTypeCode() {
        return roomTypeCode;
    }

    public void setRoomTypeCode(@NotBlank(message = "房型编码不能为空") @Size(max = 50, message = "房型编码最多50字") String roomTypeCode) {
        this.roomTypeCode = roomTypeCode;
    }

    public @NotNull(message = "房型图片不能为空") List<String> getRoomImages() {
        return roomImages;
    }

    public void setRoomImages(@NotNull(message = "房型图片不能为空") List<String> roomImages) {
        this.roomImages = roomImages;
    }

    public @NotBlank(message = "床型不能为空") String getBedType() {
        return bedType;
    }

    public void setBedType(@NotBlank(message = "床型不能为空") String bedType) {
        this.bedType = bedType;
    }

    public @NotNull(message = "可住人数不能为空") @PositiveOrZero(message = "可住人数不能小于0") Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(@NotNull(message = "可住人数不能为空") @PositiveOrZero(message = "可住人数不能小于0") Integer peopleLimit) {
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

    public @PositiveOrZero(message = "早餐数不能小于0") Integer getBreakfastCount() {
        return breakfastCount;
    }

    public void setBreakfastCount(@PositiveOrZero(message = "早餐数不能小于0") Integer breakfastCount) {
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

    public @PositiveOrZero(message = "默认价格不能小于0") BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(@PositiveOrZero(message = "默认价格不能小于0") BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public @PositiveOrZero(message = "划线价不能小于0") BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(@PositiveOrZero(message = "划线价不能小于0") BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public @PositiveOrZero(message = "默认库存不能小于0") Integer getBaseStock() {
        return baseStock;
    }

    public void setBaseStock(@PositiveOrZero(message = "默认库存不能小于0") Integer baseStock) {
        this.baseStock = baseStock;
    }

    public @NotBlank(message = "配置状态不能为空") String getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(@NotBlank(message = "配置状态不能为空") String configStatus) {
        this.configStatus = configStatus;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public @NotBlank(message = "是否可预订不能为空") String getBookableFlag() {
        return bookableFlag;
    }

    public void setBookableFlag(@NotBlank(message = "是否可预订不能为空") String bookableFlag) {
        this.bookableFlag = bookableFlag;
    }

    public @PositiveOrZero(message = "排序号不能小于0") Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(@PositiveOrZero(message = "排序号不能小于0") Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Long> getFacilityIds() {
        return facilityIds;
    }

    public void setFacilityIds(List<Long> facilityIds) {
        this.facilityIds = facilityIds;
    }
}



