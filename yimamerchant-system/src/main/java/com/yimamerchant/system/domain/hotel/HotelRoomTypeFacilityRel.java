package com.yimamerchant.system.domain.hotel;

/**
 * 房型设施关联表 hotel_room_type_facility_rel
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeFacilityRel
{
    /** 主键 */
    public Long id;

    /** 酒店编号 */
    public Long hotelId;

    /** 房型编号 */
    public Long roomTypeId;

    /** 设施编号 */
    public Long facilityId;


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

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Long getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Long facilityId) {
        this.facilityId = facilityId;
    }
}

