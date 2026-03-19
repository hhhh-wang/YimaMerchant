package com.yimamerchant.system.mapper.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.HotelFacility;
import com.yimamerchant.system.domain.hotel.query.HotelFacilityQuery;

/**
 * 酒店设施 数据层
 */
public interface HotelFacilityMapper
{
    /**
     * 查询设施列表
     */
    List<HotelFacility> selectFacilityList(HotelFacilityQuery query);
}

