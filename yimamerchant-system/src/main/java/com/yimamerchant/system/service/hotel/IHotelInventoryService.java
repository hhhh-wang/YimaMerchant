package com.yimamerchant.system.service.hotel;

import java.util.List;

import com.yimamerchant.system.domain.hotel.form.HotelInventoryBatchForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryPriceForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStockForm;
import com.yimamerchant.system.domain.hotel.form.HotelInventoryStatusForm;
import com.yimamerchant.system.domain.hotel.query.HotelInventoryQuery;
import com.yimamerchant.system.domain.hotel.vo.HotelInventoryVO;

/**
 * 库存价格 服务接口
 */
public interface IHotelInventoryService
{
    /**
     * 分页查询库存价格列表
     */
    List<HotelInventoryVO> selectInventoryList(HotelInventoryQuery query);

    /**
     * 修改单个房型默认价格
     */
    int updateBasePrice(HotelInventoryPriceForm form, String operator);

    /**
     * 修改单个房型默认库存
     */
    int updateBaseStock(HotelInventoryStockForm form, String operator);

    /**
     * 批量修改价格库存
     */
    int batchUpdateInventory(HotelInventoryBatchForm form, String operator);

    /**
     * 修改是否可预订
     */
    int changeBookableFlag(HotelInventoryStatusForm form, String operator);

    /**
     * 修改上下架状态（复用房型状态校验逻辑）
     */
    int changeSaleStatus(HotelInventoryStatusForm form, String operator);
}

