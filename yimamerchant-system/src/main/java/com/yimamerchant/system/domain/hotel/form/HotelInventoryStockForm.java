package com.yimamerchant.system.domain.hotel.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 修改单个房型默认库存
 */
public class HotelInventoryStockForm
{
    /** 房型ID */
    @NotNull(message = "房型ID不能为空")
    public Long roomTypeId;

    /** 默认库存 */
    @NotNull(message = "默认库存不能为空")
    @PositiveOrZero(message = "默认库存不能小于0")
    public Integer baseStock;

    public Long getRoomTypeId()
    {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId)
    {
        this.roomTypeId = roomTypeId;
    }

    public Integer getBaseStock()
    {
        return baseStock;
    }

    public void setBaseStock(Integer baseStock)
    {
        this.baseStock = baseStock;
    }
}

