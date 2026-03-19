package com.yimamerchant.system.mapper.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.HotelRoomType;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryBatchForm;
import com.yimamerchant.system.domain.hotel.query.HotelInventoryQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelInventoryVO;

/**
 * 库存价格 数据层（默认价格/默认库存，基于 hotel_room_type）
 */
public interface HotelInventoryMapper
{
    /**
     * 分页查询库存价格列表
     */
    List<HotelInventoryVO> selectInventoryList(HotelInventoryQuery query);

    /**
     * 修改单个房型默认价格
     */
    int updateBasePrice(HotelRoomType roomType);

    /**
     * 修改单个房型默认库存
     */
    int updateBaseStock(HotelRoomType roomType);

    /**
     * 批量修改价格/库存
     */
    int batchUpdateInventory(HotelInventoryBatchForm form);

    /**
     * 修改是否可预订
     */
    int updateBookableFlag(HotelRoomType roomType);
}

