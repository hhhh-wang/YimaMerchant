package com.yimamerchant.system.mapper.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.HotelRoomTypeFacilityRel;

/**
 * 房型设施关联 数据层
 */
public interface HotelRoomTypeFacilityRelMapper
{
    /**
     * 根据房型ID查询已绑定设施ID列表
     */
    List<Long> selectFacilityIdsByRoomTypeId(Long roomTypeId);

    /**
     * 根据房型ID删除所有关联
     */
    int deleteByRoomTypeId(Long roomTypeId);

    /**
     * 批量新增房型设施关联
     */
    int batchInsert(List<HotelRoomTypeFacilityRel> list);
}

