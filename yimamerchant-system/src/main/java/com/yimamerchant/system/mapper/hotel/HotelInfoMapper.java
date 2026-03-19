package com.yimamerchant.system.mapper.hotel;

import com.yimamerchant.system.domain.hotel.HotelInfo;

/**
 * 酒店信息 数据层
 */
public interface HotelInfoMapper
{
    /**
     * 按商家编号查询酒店信息（单店模型）
     */
    HotelInfo selectByMerchantId(Long merchantId);

    /**
     * 新增酒店信息
     */
    int insertHotelInfo(HotelInfo hotelInfo);

    /**
     * 更新酒店信息（按主键）
     */
    int updateHotelInfo(HotelInfo hotelInfo);

    /**
     * 更新酒店状态（按主键、商家隔离）
     */
    int updateStatus(HotelInfo hotelInfo);
}

