package com.yimamerchant.system.domain.hotel.form;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 修改单个房型默认价格
 */
public class HotelInventoryPriceForm
{
    /** 房型ID */
    @NotNull(message = "房型ID不能为空")
    public Long roomTypeId;

    /** 默认价格（销售价） */
    @NotNull(message = "默认价格不能为空")
    @PositiveOrZero(message = "默认价格不能小于0")
    public BigDecimal basePrice;

    /** 划线价（可选） */
    @PositiveOrZero(message = "划线价不能小于0")
    public BigDecimal marketPrice;

    public Long getRoomTypeId()
    {
        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId)
    {
        this.roomTypeId = roomTypeId;
    }

    public BigDecimal getBasePrice()
    {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice)
    {
        this.basePrice = basePrice;
    }

    public BigDecimal getMarketPrice()
    {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice)
    {
        this.marketPrice = marketPrice;
    }
}

