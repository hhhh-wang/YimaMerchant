package com.yimamerchant.system.hotel.domain;

import com.yimamerchant.common.core.domain.BaseEntity;

public class HotelImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long hotelId;
    private Long roomTypeId;
    private String imageType;
    private String imageUrl;
    private String imageName;
    private Integer sortNum;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }
    public Long getRoomTypeId() { return roomTypeId; }
    public void setRoomTypeId(Long roomTypeId) { this.roomTypeId = roomTypeId; }
    public String getImageType() { return imageType; }
    public void setImageType(String imageType) { this.imageType = imageType; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    public Integer getSortNum() { return sortNum; }
    public void setSortNum(Integer sortNum) { this.sortNum = sortNum; }
}
