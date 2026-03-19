package com.yimamerchant.system.service.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.HotelFacility;
import com.yimamerchant.system.domain.hotel.form.HotelRoomTypeFacilityForm;
import com.yimamerchant.system.domain.hotel.query.HotelFacilityQuery;

/**
 * 酒店设施 服务接口
 */
public interface IHotelFacilityService
{
    /**
     * 查询设施列表
     */
    List<HotelFacility> selectFacilityList(HotelFacilityQuery query);

    /**
     * 查询房型已绑定设施ID列表
     */
    List<Long> selectRoomTypeFacilityIds(Long roomTypeId);

    /**
     * 保存房型设施绑定
     */
    int saveRoomTypeFacilities(HotelRoomTypeFacilityForm form);
}

