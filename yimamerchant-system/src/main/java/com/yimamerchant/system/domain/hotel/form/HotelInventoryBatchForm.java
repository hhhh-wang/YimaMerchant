package com.yimamerchant.system.domain.hotel.form;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * 批量修改价格库存
 */
public class HotelInventoryBatchForm
{
    /** 房型ID列表 */
    @NotEmpty(message = "房型ID列表不能为空")
    public List<Long> roomTypeIds;

    /** 默认价格（销售价），仅当需要更新价格时传入 */
    @PositiveOrZero(message = "默认价格不能小于0")
    public BigDecimal basePrice;

    /** 划线价（可选），仅在需要更新价格时传入 */
    @PositiveOrZero(message = "划线价不能小于0")
    public BigDecimal marketPrice;

    /** 默认库存，仅当需要更新库存时传入 */
    @PositiveOrZero(message = "默认库存不能小于0")
    public Integer baseStock;

    /** 商家编号（服务层注入，不由前端传参） */
    public Long merchantId;

    /** 操作人（服务层注入，不由前端传参） */
    public String updateBy;

    public List<Long> getRoomTypeIds()
    {
        return roomTypeIds;
    }

    public void setRoomTypeIds(List<Long> roomTypeIds)
    {
        this.roomTypeIds = roomTypeIds;
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

    public Integer getBaseStock()
    {
        return baseStock;
    }

    public void setBaseStock(Integer baseStock)
    {
        this.baseStock = baseStock;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }
}

