package com.yimamerchant.system.domain.hotel.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存价格列表 VO
 */
public class HotelInventoryVO
{
    /** 房型编号 */
    public Long id;

    /** 房型名称 */
    public String roomTypeName;

    /** 销售价（映射 basePrice） */
    public BigDecimal salePrice;

    /** 划线价 */
    public BigDecimal marketPrice;

    /** 默认库存 */
    public Integer baseStock;

    /** 已售数量 */
    public Integer soldNum;

    /** 剩余可售（baseStock - soldNum，最小为 0） */
    public Integer availableNum;

    /** 上架状态 */
    public String saleStatus;

    /** 是否可预订 */
    public String bookableFlag;

    /** 更新时间 */
    public Date updateTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getRoomTypeName()
    {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName)
    {
        this.roomTypeName = roomTypeName;
    }

    public BigDecimal getSalePrice()
    {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice)
    {
        this.salePrice = salePrice;
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

    public Integer getSoldNum()
    {
        return soldNum;
    }

    public void setSoldNum(Integer soldNum)
    {
        this.soldNum = soldNum;
    }

    public Integer getAvailableNum()
    {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum)
    {
        this.availableNum = availableNum;
    }

    public String getSaleStatus()
    {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus)
    {
        this.saleStatus = saleStatus;
    }

    public String getBookableFlag()
    {
        return bookableFlag;
    }

    public void setBookableFlag(String bookableFlag)
    {
        this.bookableFlag = bookableFlag;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}

