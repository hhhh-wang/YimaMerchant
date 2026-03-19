package com.yimamerchant.system.mapper.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.HotelRoomType;
import com.yimamerchant.system.domain.hotel.query.HotelRoomTypeQuery;

/**
 * 房型管理 数据层
 */
public interface HotelRoomTypeMapper
{
    /**
     * 分页查询房型列表
     */
    List<HotelRoomType> selectRoomTypeList(HotelRoomTypeQuery query);

    /**
     * 根据ID查询房型
     */
    HotelRoomType selectRoomTypeById(Long id);

    /**
     * 新增房型
     */
    int insertRoomType(HotelRoomType roomType);

    /**
     * 更新房型
     */
    int updateRoomType(HotelRoomType roomType);

    /**
     * 逻辑删除房型
     */
    int logicalDeleteRoomType(HotelRoomType roomType);

    /**
     * 修改配置状态
     */
    int updateConfigStatus(HotelRoomType roomType);

    /**
     * 修改上架状态
     */
    int updateSaleStatus(HotelRoomType roomType);
}

