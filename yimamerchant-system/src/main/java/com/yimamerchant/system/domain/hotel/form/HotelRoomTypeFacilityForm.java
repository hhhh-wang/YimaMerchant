package com.yimamerchant.system.domain.hotel.form;

import java.util.List;

import jakarta.validation.constraints.NotNull;

/**
 * 房型设施绑定表单
 *
 * 注意：按你的要求此类不生成 getter/setter，你可后续自行生成。
 */
public class HotelRoomTypeFacilityForm
{
    /** 房型ID */
    @NotNull(message = "房型ID不能为空")
    public Long roomTypeId;

    /** 设施ID列表 */
    public List<Long> facilityIds;

    public @NotNull(message = "房型ID不能为空") Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(@NotNull(message = "房型ID不能为空") Long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public List<Long> getFacilityIds() {
        return facilityIds;
    }

    public void setFacilityIds(List<Long> facilityIds) {
        this.facilityIds = facilityIds;
    }
}

