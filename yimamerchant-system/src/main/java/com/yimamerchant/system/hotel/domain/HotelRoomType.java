package com.yimamerchant.system.hotel.domain;

import java.math.BigDecimal;
import java.util.List;
import com.yimamerchant.common.annotation.Excel;
import com.yimamerchant.common.annotation.Excel.ColumnType;
import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelRoomType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    @Excel(name = "房型ID", cellType = ColumnType.NUMERIC)
    private Long id;
    private Long hotelId;
    private String hotelName;
    private String roomTypeCode;
    @Excel(name = "房型名称")
    private String roomTypeName;
    private String roomTypeNameEn;
    private Integer adultCount;
    private Integer childCount;
    @Excel(name = "床型")
    private String bedType;
    private String bedDesc;
    @Excel(name = "面积")
    private BigDecimal areaSize;
    @Excel(name = "窗户")
    private String windowStatus;
    private String bathroomType;
    private String isSmoking;
    private String isBreakfast;
    private String coverImage;
    private String roomDesc;
    private String defaultRefundRule;
    private Integer minBookingRooms;
    private Integer maxBookingRooms;
    private Integer minBookingNights;
    private Integer maxBookingNights;
    @Excel(name = "状态")
    private String saleStatus;
    private Integer sortNum;
    private List<HotelImage> imageList;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public String getHotelName() { return hotelName; }
    public void setHotelName(String hotelName) { this.hotelName = hotelName; }
    public String getRoomTypeCode() { return roomTypeCode; }
    public void setRoomTypeCode(String roomTypeCode) { this.roomTypeCode = roomTypeCode; }
    public String getRoomTypeName() { return roomTypeName; }
    public void setRoomTypeName(String roomTypeName) { this.roomTypeName = roomTypeName; }
    public String getRoomTypeNameEn() { return roomTypeNameEn; }
    public void setRoomTypeNameEn(String roomTypeNameEn) { this.roomTypeNameEn = roomTypeNameEn; }
    public Integer getAdultCount() { return adultCount; }
    public void setAdultCount(Integer adultCount) { this.adultCount = adultCount; }
    public Integer getChildCount() { return childCount; }
    public void setChildCount(Integer childCount) { this.childCount = childCount; }
    public String getBedType() { return bedType; }
    public void setBedType(String bedType) { this.bedType = bedType; }
    public String getBedDesc() { return bedDesc; }
    public void setBedDesc(String bedDesc) { this.bedDesc = bedDesc; }
    public BigDecimal getAreaSize() { return areaSize; }
    public void setAreaSize(BigDecimal areaSize) { this.areaSize = areaSize; }
    public String getWindowStatus() { return windowStatus; }
    public void setWindowStatus(String windowStatus) { this.windowStatus = windowStatus; }
    public String getBathroomType() { return bathroomType; }
    public void setBathroomType(String bathroomType) { this.bathroomType = bathroomType; }
    public String getIsSmoking() { return isSmoking; }
    public void setIsSmoking(String isSmoking) { this.isSmoking = isSmoking; }
    public String getIsBreakfast() { return isBreakfast; }
    public void setIsBreakfast(String isBreakfast) { this.isBreakfast = isBreakfast; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public String getRoomDesc() { return roomDesc; }
    public void setRoomDesc(String roomDesc) { this.roomDesc = roomDesc; }
    public String getDefaultRefundRule() { return defaultRefundRule; }
    public void setDefaultRefundRule(String defaultRefundRule) { this.defaultRefundRule = defaultRefundRule; }
    public Integer getMinBookingRooms() { return minBookingRooms; }
    public void setMinBookingRooms(Integer minBookingRooms) { this.minBookingRooms = minBookingRooms; }
    public Integer getMaxBookingRooms() { return maxBookingRooms; }
    public void setMaxBookingRooms(Integer maxBookingRooms) { this.maxBookingRooms = maxBookingRooms; }
    public Integer getMinBookingNights() { return minBookingNights; }
    public void setMinBookingNights(Integer minBookingNights) { this.minBookingNights = minBookingNights; }
    public Integer getMaxBookingNights() { return maxBookingNights; }
    public void setMaxBookingNights(Integer maxBookingNights) { this.maxBookingNights = maxBookingNights; }
    public String getSaleStatus() { return saleStatus; }
    public void setSaleStatus(String saleStatus) { this.saleStatus = saleStatus; }
    public Integer getSortNum() { return sortNum; }
    public void setSortNum(Integer sortNum) { this.sortNum = sortNum; }
    public List<HotelImage> getImageList() { return imageList; }
    public void setImageList(List<HotelImage> imageList) { this.imageList = imageList; }
}
